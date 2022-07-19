package com.example.springsecmeta.config.auth;

/**
 * @author Taewoo
 */


import com.example.springsecmeta.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

// Security가 "/login" 주소 요청을 낚아채서 로그인을 진행시킨디ㅏ.
// 진행이 완료가 되면 Seesion에 Key값이 (SecurityContextHolder)인 것을 저장한다.
// Value로 오브젝트가 들어가는데 Authentication 객체만 들어갈 수 있다.
// Authentication 안에 User정보가 있어야 됨.
// User 오브젝트타입 => UserDetails 타입 객체임.

// Security Session -> Authentication -> UserDetails


public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // User의 권한을 리턴해야되는 메서드
        // 하지만 리턴 타입이 (? extends GrantedAuthority) 이기 때문에
        // String이나 Enum으로 권한을 정의할게 아니라 새로운 권한 클래스를 만들어야댐.
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되었는가?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 정지되었는가?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
