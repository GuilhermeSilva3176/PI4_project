package com.fitfinance.util;

import com.fitfinance.domain.Token;
import com.fitfinance.domain.TokenType;
import com.fitfinance.domain.User;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    public Token createValidToken(String jwtToken, User user) {
        return Token.builder()
                .tokenType(TokenType.BEARER)
                .expired(false)
                .tokenString(jwtToken)
                .user(user)
                .build();
    }
}
