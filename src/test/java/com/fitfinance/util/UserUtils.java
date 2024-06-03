package com.fitfinance.util;

import com.fitfinance.domain.User;
import com.fitfinance.request.UserPostRoleAdminRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserUtils {
    public User createValidUser() {
        return User.builder()
                .name("Lucas")
                .cpf("111.222.333-44")
                .phone("11 91234-5678")
                .roles("USER")
                .income(1000.0)
                .email("lucas@fitfinance.com")
                .password("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .birthdate("01/01/1990")
                .build();
    }

    public UserPostRoleAdminRequest createValidAdminUser() {
        return UserPostRoleAdminRequest.builder()
                .name("Lucas")
                .cpf("000.000.000-00")
                .phone("11 91234-5678")
                .income(0)
                .email("test.admin@fitfinance.com")
                .password("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .birthdate("01/01/1990")
                .build();
    }

    public User createValidWithRandomEmailAndCpfUser() {
        return User.builder()
                .name("Lucas")
                .cpf(generateCpf())
                .phone("11 91234-5678")
                .roles("USER")
                .income(1000.0)
                .tokens(Collections.emptyList())
                .email(generateEmail())
                .password("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .birthdate("01/01/1990")
                .build();
    }

    public String generateEmail() {
        return "user" + System.nanoTime() + "@fitfinance.com";
    }

    public String generateCpf() {
        return "111." + System.nanoTime() + ".333-44";
    }
}
