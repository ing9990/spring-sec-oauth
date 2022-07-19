package com.example.springsecmeta.repository;

import com.example.springsecmeta.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);

}
