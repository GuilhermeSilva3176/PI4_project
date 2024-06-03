package com.fitfinance.config;

import com.fitfinance.domain.Token;
import com.fitfinance.domain.TokenType;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.UserMapper;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.request.UserPostRoleAdminRequest;
import com.fitfinance.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class AdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper mapper;
    private final JwtService service;


    @Override
    @Transactional
    public void run(String... args) {
        var userAdmin = userRepository.findByRoles("ADMIN");
//        var userActuator = userRepository.findByRoles("ACTUATOR");

        userAdmin.ifPresentOrElse(user -> log.info("User with role 'ADMIN' already exists: {}", user),
                () -> {
                    var userPostAdminRequest = UserPostRoleAdminRequest.builder()
                            .name("ADMIN")
                            .email("admin@fitfinance.com")
                            .password("fit-finance-admin")
                            .cpf("0000")
                            .phone("0000")
                            .income(0)
                            .birthdate("01/01/1990")
                            .build();

                    createRoleSpecificUser(mapper.toUser(userPostAdminRequest));
                });

//        userActuator.ifPresentOrElse(user -> log.info("User with role 'ACTUATOR' already exists: {}", user),
//                () -> {
//                    UserPostRoleSpecificRequest userPostActuatorRequest = UserPostRoleSpecificRequest.builder()
//                            .name("ACTUATOR")
//                            .email("actuator@fitfinance.com")
//                            .password("fit-finance-actuator")
//                            .cpf("0000")
//                            .phone("0000")
//                            .income(0)
//                            .birthdate("01/01/1990")
//                            .build();
//
//                    createRoleSpecificUser(mapper.toUser(userPostActuatorRequest));
//                });
    }

    private void createRoleSpecificUser(User user) {
        String jwtToken = service.generateToken(user);
        var token = Token.builder()
                .user(user)
                .tokenString(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        user.setTokens(List.of(token));
        tokenRepository.save(token);
        userRepository.save(user);
    }
}
