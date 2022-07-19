package com.example.springsecmeta.config;

/**
 * @author Taewoo
 */

import com.example.springsecmeta.service.oauth.google.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// secured Annotaion 활성화 = true, preAuthorize Annotation 활성화 = true, postAuthorize Annotation 활성화

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()

                .and()

                // FORM 로그인
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")  // /login 주소로 호출이 되면 Security에서 낚아채서 로그인을 진행해준다.
                .defaultSuccessUrl("/")       // 로그인 성공시 주소

                .and()

                
                // OAUTH CLIENT를 사용한 구글 로그인
                .oauth2Login()
                .loginPage("/loginForm")

                // 구글 로그인이 완료된 후의 후처리가 필요하다.
                /*
                    1. 코드받기 (인증) 발급
                    2. 엑세스토큰 (권한) 발급
                    3. 사용자 프로필 정보를 가져옴.
                    4. 그 정보를 토대로 회원가입 자동 진행 OR
                 */

                .userInfoEndpoint()
                .userService(principalOauth2UserService);

    }
}
