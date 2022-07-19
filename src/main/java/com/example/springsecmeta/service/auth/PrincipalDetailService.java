package com.example.springsecmeta.service.auth;

/**
 * @author Taewoo
 */


import com.example.springsecmeta.config.auth.PrincipalDetails;
import com.example.springsecmeta.domain.User;
import com.example.springsecmeta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


// Security에서 loginPrecessingUrl을 ("/login")으로 걸었다.
// "/login" 요청이 오면 자동으로 UserDetailsService 타입으로 ioC되어있는 loadUserByUsername 함수가 호출된다.


@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        return user != null ? new PrincipalDetails(user) : null;
    }
}












