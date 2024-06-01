package com.fitfinance.controller;

import com.fitfinance.config.TestRestTemplateConfig;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.response.AuthenticationResponse;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.util.AsyncTestUtils;
import com.fitfinance.util.AuthenticationUtils;
import com.fitfinance.util.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Log4j2
class AuthenticationControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthenticationUtils utils;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("register user returns authentication response when successful")
    void registerUser_ReturnsAuthenticationResponse_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        ResponseEntity<UserGetResponse> response = restTemplate.postForEntity("/api/v1/auth/register",
                registerRequest, UserGetResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(userRepository.findByEmail(user.getEmail())).isPresent();
        Assertions.assertThat(userRepository.findByEmail(user.getEmail()).get().getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("authenticate user returns authentication response when successful")
    void authenticateUser_ReturnsAuthenticationResponse_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var authenticationRequest = utils.toAuthenticationRequest(user);

        var registerRequest = utils.toRegisterRequest(user);
        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken())).isNotNull();
    }

    @Test
    @DisplayName("refreshToken returns AuthenticationResponse when successful")
    void refreshToken_ReturnsAuthenticationResponse_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);

        ResponseEntity<AuthenticationResponse> authenticationResponse = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        AsyncTestUtils.waitFor(Duration.ofSeconds(1));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(authenticationResponse.getBody()).getAccessToken());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/refresh-token",
                HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken())).isNotNull();
        Assertions.assertThat(response.getBody().getAccessToken()).isNotNull()
                .isNotEqualTo(Objects.requireNonNull(authenticationResponse.getBody()).getAccessToken());
        Assertions.assertThat(response.getBody().getAccessToken()).isNotNull();
        Assertions.assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken()).get()).isNotNull();
    }


}