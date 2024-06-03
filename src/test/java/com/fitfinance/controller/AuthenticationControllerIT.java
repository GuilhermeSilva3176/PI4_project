package com.fitfinance.controller;

import com.fitfinance.config.TestRestTemplateConfig;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.response.AuthenticationResponse;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.util.TestUtils;
import com.fitfinance.util.AuthenticationUtils;
import com.fitfinance.util.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

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
    @DisplayName("register() returns User when successful")
    void register_ReturnsUser_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        ResponseEntity<UserGetResponse> response = restTemplate.postForEntity("/api/v1/auth/register",
                registerRequest, UserGetResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(userRepository.findByEmail(user.getEmail())).isPresent();
        assertThat(userRepository.findByEmail(user.getEmail()).get().getEmail()).isNotNull()
                .isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("register() returns 403 Forbidden when register with email that already exists")
    void register_Returns403Forbidden_WhenRegisterWithEmailThatAlreadyExists() {
        var user = userUtils.createValidUser();
        var userToFail = userUtils.createValidUser();
        userToFail.setCpf("0.0.0.0-0");

        var registerRequest = utils.toRegisterRequest(user);
        ResponseEntity<UserGetResponse> successfulResponse = restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);

        ResponseEntity<UserGetResponse> responseToFail = restTemplate.postForEntity("/api/v1/auth/register",
                registerRequest, UserGetResponse.class);

        assertThat(successfulResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(successfulResponse.getBody()).isNotNull();

        assertThat(responseToFail.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(responseToFail.getBody()).isNull();

        assertThat(userRepository.findByCpf(userToFail.getCpf())).isEmpty();

        assertThat(userRepository.findByEmail(user.getEmail())).isPresent();
        assertThat(userRepository.findByEmail(user.getEmail()).get().getEmail()).isNotNull()
                .isEqualTo(successfulResponse.getBody().getEmail());
    }

    @Test
    @DisplayName("authenticate() returns AuthenticationResponse when successful")
    void authenticate_ReturnsAuthenticationResponse_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken())).isNotNull();
    }

    @Test
    @DisplayName("authenticate() returns 403 Forbidden when password is wrong")
    void authenticate_Returns403Forbidden_WhenPasswordIsWrong() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        authenticationRequest.setPassword("wrong-password");

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("refreshToken() returns AuthenticationResponse when successful")
    void refreshToken_ReturnsAuthenticationResponse_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);

        ResponseEntity<AuthenticationResponse> authenticationResponse = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(authenticationResponse.getBody()).getAccessToken());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/refresh-token",
                HttpMethod.POST, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken())).isNotNull();
        assertThat(response.getBody().getAccessToken()).isNotNull()
                .isNotEqualTo(Objects.requireNonNull(authenticationResponse.getBody()).getAccessToken());
        assertThat(response.getBody().getAccessToken()).isNotNull();
        assertThat(tokenRepository.findByTokenString(response.getBody().getAccessToken()).get()).isNotNull();
    }

    @Test
    @DisplayName("refreshToken() returns 403 Forbidden when request does not have token")
    void refreshToken_Returns403Forbidden_WhenRequestDoesNotHaveToken() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);

        restTemplate.postForEntity("/api/v1/auth/authenticate", authenticationRequest, AuthenticationResponse.class);

        TestUtils.waitFor(Duration.ofSeconds(1));

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange("/api/v1/auth/refresh-token",
                HttpMethod.POST, null, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("logout() returns 204 No Content when successful")
    void logout_Returns204NoContent_WhenSuccessful() {
        var user = userUtils.createValidUser();
        var registerRequest = utils.toRegisterRequest(user);
        var authenticationRequest = utils.toAuthenticationRequest(user);

        restTemplate.postForEntity("/api/v1/auth/register", registerRequest, UserGetResponse.class);

        ResponseEntity<AuthenticationResponse> authenticationResponse = restTemplate.postForEntity("/api/v1/auth/authenticate",
                authenticationRequest, AuthenticationResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(authenticationResponse.getBody()).getAccessToken());

        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/auth/logout", HttpMethod.POST, new HttpEntity<>(headers), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}