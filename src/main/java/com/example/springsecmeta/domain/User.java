package com.example.springsecmeta.domain;

/**
 * @author Taewoo
 */


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.*;


@Entity
@ToString
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private String email;

    private String role;

    private String provider; // google, kakao ...

    private String providerId; // google Id, Kakao Id ...

    @CreationTimestamp
    private Timestamp createDate;
}
