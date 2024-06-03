package com.fitfinance.controller;

import com.fitfinance.config.PasswordEncoderConfig;
import com.fitfinance.config.TestRestTemplateConfig;
import com.fitfinance.domain.Token;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.UserMapper;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.request.ChangePasswordRequest;
import com.fitfinance.request.UserPostRequest;
import com.fitfinance.request.UserPutRequest;
import com.fitfinance.response.AuthenticationResponse;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.response.UserPostResponse;
import com.fitfinance.service.JwtService;
import com.fitfinance.util.TokenUtils;
import com.fitfinance.util.UserUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Log4j2
class UserControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoderConfig passwordEncoder;

    private HttpEntity<Void> regularUserHeader;
    private HttpEntity<Void> adminUserHeader;
    private HttpEntity<Void> wrongHeader;

    private Long authenticatedRegularUserId;

    @BeforeEach
    void setUpRegularUser() {
        var user = userUtils.createValidUser();

        ResponseEntity<UserGetResponse> registerResponse = restTemplate.postForEntity("api/v1/auth/register", user, UserGetResponse.class);
        authenticatedRegularUserId = Objects.requireNonNull(registerResponse.getBody()).getId();
        var response = restTemplate.postForEntity("api/v1/auth/authenticate", user, AuthenticationResponse.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(Objects.requireNonNull(response.getBody()).getAccessToken());

        regularUserHeader = new HttpEntity<>(httpHeaders);
    }

    @BeforeEach
    void setUpAdminUser() {
        var userPostRoleAdminRequest = userUtils.createValidAdminUser();
        User user = userMapper.toUser(userPostRoleAdminRequest);

        String jwtToken = jwtService.generateToken(user);
        User savedUser = userRepository.save(user);

        var token = tokenUtils.createValidToken(jwtToken, savedUser);
        Token savedToken = tokenRepository.save(token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(savedToken.getTokenString());

        adminUserHeader = new HttpEntity<>(httpHeaders);
    }

    @BeforeEach
    void setUpWrongHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth("wrong_token");

        wrongHeader = new HttpEntity<>(httpHeaders);
    }

    @Test
    @DisplayName("findAll() returns list of users when user is admin")
    void findAll_ReturnsListOfUsers_WhenUserIsAdmin() {
        userRepository.save(userUtils.createValidWithRandomEmailAndCpfUser());
        userRepository.save(userUtils.createValidWithRandomEmailAndCpfUser());
        userRepository.save(userUtils.createValidWithRandomEmailAndCpfUser());

        ResponseEntity<List<UserGetResponse>> response = restTemplate.exchange("api/v1/users", HttpMethod.GET,
                adminUserHeader, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        //Expected 5 users because two of them are created in the @BeforeEach method, being USER and ADMIN
        assertThat(response.getBody()).isNotEmpty().hasSize(5);
    }

    @Test
    @DisplayName("findAll() returns 403 Forbidden when header is wrong")
    void findAll_Returns403Forbidden_WhenHeaderIsWrong() {
        ResponseEntity<List<UserGetResponse>> response = restTemplate.exchange("api/v1/users", HttpMethod.GET,
                wrongHeader, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("findAll() returns 403 Forbidden when user is not admin")
    void findAll_Returns403Forbidden_WhenUserIsNotAdmin() {
        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users", HttpMethod.GET,
                regularUserHeader, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("findById() returns user when user is admin")
    void findById_ReturnsUser_WhenUserIsAdmin() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);

        ResponseEntity<UserGetResponse> response = restTemplate.exchange("api/v1/users/" + savedUser.getId(),
                HttpMethod.GET, adminUserHeader, UserGetResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("findById() returns 403 Forbidden when user is not admin")
    void findById_Returns403Forbidden_WhenUserIsNotAdmin() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users/" + savedUser.getId(),
                HttpMethod.GET, regularUserHeader, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("createUser() returns 201 Created when user is admin")
    void createUser_Returns201Created_WhenUserIsAdmin() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        var userPostRequest = UserPostRequest.builder()
                .name(user.getName())
                .cpf(user.getCpf())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .income(3000.0)
                .birthdate(user.getBirthdate())
                .build();

        ResponseEntity<UserPostResponse> response = restTemplate.exchange("api/v1/users", HttpMethod.POST,
                new HttpEntity<>(userPostRequest, adminUserHeader.getHeaders()), UserPostResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("createUser() returns 403 Forbidden when user is not admin")
    void createUser_Returns403Forbidden_WhenUserIsNotAdmin() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users", HttpMethod.POST,
                new HttpEntity<>(user, regularUserHeader.getHeaders()), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("updateUser() returns 204 No Content when successful")
    void updateUser_Returns204NoContent_WhenSuccessful() {
        User foundUser = userRepository.findById(authenticatedRegularUserId).get();
        var user = UserPutRequest.builder()
                .id(authenticatedRegularUserId)
                .name(foundUser.getName())
                .cpf(foundUser.getCpf())
                .email(foundUser.getEmail())
                .phone(foundUser.getPhone())
                .income(3300.0)
                .birthdate(foundUser.getBirthdate())
                .build();

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users", HttpMethod.PUT,
                new HttpEntity<>(user, regularUserHeader.getHeaders()), Void.class);

        User updatedUser = userRepository.findById(authenticatedRegularUserId).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(authenticatedRegularUserId);
        assertThat(updatedUser.getIncome()).isEqualTo(3300.0);
    }

    @Test
    @DisplayName("updateUser() returns 403 Forbidden when id is different from authenticated user")
    void updateUser_Returns403Forbidden_WhenIdIsDifferentFromAuthenticatedUser() {
        User foundUser = userRepository.findById(authenticatedRegularUserId).get();
        var user = UserPutRequest.builder()
                .id(foundUser.getId() + 1L)
                .name(foundUser.getName())
                .cpf(foundUser.getCpf())
                .email(foundUser.getEmail())
                .phone(foundUser.getPhone())
                .income(3300.0)
                .birthdate(foundUser.getBirthdate())
                .build();

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users", HttpMethod.PUT,
                new HttpEntity<>(user, regularUserHeader.getHeaders()), Void.class);

        Optional<User> updatedUser = userRepository.findById(authenticatedRegularUserId + 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(updatedUser).isEmpty();
    }

    @Test
    @DisplayName("changePassword() returns 200 OK when successful")
    void changePassword_Returns200Ok_WhenSuccessful() {
        User user = userRepository.findById(authenticatedRegularUserId).get();
        log.info("User password: {}", user.getPassword());
        var changePasswordRequest = ChangePasswordRequest.builder()
                .currentPassword("{bcrypt}$2a$10$RCe/7.jx6GajRcLYHg.1uuKii9LaUPvhMRe21nNDf1cAaCqweLBAO")
                .newPassword("new_password")
                .confirmationPassword("new_password")
                .build();

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users", HttpMethod.PATCH,
                new HttpEntity<>(changePasswordRequest, regularUserHeader.getHeaders()), Void.class);

        user = userRepository.findById(authenticatedRegularUserId).get();

        log.info("User password: {}", user.getPassword());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(passwordEncoder.passwordEncoder().matches("new_password", user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("delete() returns 204 No Content when user is admin")
    void delete_Returns204NoContent_WhenUserIsAdmin() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users/" + savedUser.getId(),
                HttpMethod.DELETE, adminUserHeader, Void.class);

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    @DisplayName("delete() returns 403 Forbidden when user is not admin and tries to delete another user")
    void delete_Returns403Forbidden_WhenUserIsNotAdminAndTriesToDeleteAnotherUser() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users/" + savedUser.getId(),
                HttpMethod.DELETE, regularUserHeader, Void.class);

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(deletedUser).isPresent();
    }

    @Test
    @DisplayName("delete() returns 204 no content when user tries to delete your account")
    void delete_Returns403Forbidden_WhenUserTriesToDeleteYourAccount() {
        ResponseEntity<Void> response = restTemplate.exchange("api/v1/users/" + authenticatedRegularUserId,
                HttpMethod.DELETE, regularUserHeader, Void.class);

        Optional<User> deletedUser = userRepository.findById(authenticatedRegularUserId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(deletedUser).isEmpty();
    }
}
