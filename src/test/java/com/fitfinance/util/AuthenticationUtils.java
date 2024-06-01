package com.fitfinance.util;

import com.fitfinance.domain.User;
import com.fitfinance.request.AuthenticationRequest;
import com.fitfinance.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {
    public RegisterRequest toRegisterRequest(User user) {
        return RegisterRequest.builder()
                .name(user.getName())
                .cpf(user.getCpf())
                .phone(user.getPhone())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthdate(user.getBirthdate())
                .build();
    }

    public AuthenticationRequest toAuthenticationRequest(User user) {
        return AuthenticationRequest.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
