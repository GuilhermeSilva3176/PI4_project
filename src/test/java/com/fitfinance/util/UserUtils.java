package com.fitfinance.util;

import com.fitfinance.domain.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserUtils {
    public User createValidUser() {
        return User.builder()
                .id(99L)
                .name("Lucas")
                .cpf("111.222.333-44")
                .phone("11 91234-5678")
                .roles("USER")
                .income(1000.0)
                .tokens(Collections.emptyList())
                .email("lucas@fitfinance.com")
                .password("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .birthdate("01/01/1990")
                .build();
    }

    public User createValidAdminUser() {
        return User.builder()
                .id(99L)
                .name("Lucas")
                .cpf("111.222.333-44")
                .phone("11 91234-5678")
                .roles("USER")
                .income(0)
                .tokens(Collections.emptyList())
                .email("lucas.admin@fitfinance.com")
                .password("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .birthdate("01/01/1990")
                .build();
    }
}
