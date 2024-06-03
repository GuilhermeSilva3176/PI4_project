package com.fitfinance.controller;

import com.fitfinance.config.TestRestTemplateConfig;
import com.fitfinance.domain.Finance;
import com.fitfinance.domain.Token;
import com.fitfinance.domain.User;
import com.fitfinance.mapper.UserMapper;
import com.fitfinance.repository.FinanceRepository;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.response.*;
import com.fitfinance.service.JwtService;
import com.fitfinance.util.FinanceUtils;
import com.fitfinance.util.TestUtils;
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

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRestTemplateConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Log4j2
class FinanceControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private FinanceUtils financeUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private FinanceRepository financeRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;

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
    @DisplayName("findAll() returns list of finances when user is admin")
    void findAll_ReturnsListOfUsers_WhenUserIsAdmin() {
        financeRepository.save(financeUtils.createValidFinance());
        financeRepository.save(financeUtils.createValidFinance());
        financeRepository.save(financeUtils.createValidFinance());

        ResponseEntity<List<UserGetResponse>> response = restTemplate.exchange("api/v1/finances", HttpMethod.GET,
                adminUserHeader, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty().hasSize(3);
    }

    @Test
    @DisplayName("findAll() returns 403 Forbidden when user is not admin")
    void findAll_Returns403Forbidden_WhenUserIsNotAdmin() {
        ResponseEntity<Void> response = restTemplate.exchange("api/v1/finances", HttpMethod.GET,
                regularUserHeader, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("findById() returns finance when user is admin")
    void findById_ReturnsFinance_WhenUserIsAdmin() {
        var finance = financeUtils.createValidFinance();
        var savedFinance = financeRepository.save(finance);

        ResponseEntity<FinanceGetResponse> response = restTemplate.exchange("api/v1/finances/" + savedFinance.getId(),
                HttpMethod.GET, adminUserHeader, FinanceGetResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedFinance.getId());
    }

    @Test
    @DisplayName("findByUserId() returns 200 Ok when authenticated user id is equal to finance user id")
    void findById_Returns200Ok_WhenAuthenticatedUserIdIsEqualToFinanceUserId() {
        User savedUser = userRepository.findById(authenticatedRegularUserId).get();
        var finance = financeUtils.createValidFinanceWithUser(savedUser);
        financeRepository.save(finance);
        financeRepository.save(finance);
        financeRepository.save(finance);

        ResponseEntity<List<FinanceGetResponse>> response = restTemplate.exchange("api/v1/finances/by-user-id",
                HttpMethod.GET, regularUserHeader, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
        assertThat(response.getBody()).hasSize(1);
        response.getBody().forEach(financeGetResponse -> assertThat(financeGetResponse.getUser().getId())
                .isEqualTo(savedUser.getId()));
    }

    @Test
    @DisplayName("findByUserId() returns empty list when authenticated user is different from finance user")
    void findById_ReturnsEmptyList_WhenAuthenticatedUserIsDifferentFromFinanceUser() {
        User savedUser = userRepository.save(userUtils.createValidWithRandomEmailAndCpfUser());
        var finance = financeUtils.createValidFinance();
        finance.setUser(savedUser);
        financeRepository.save(finance);
        financeRepository.save(finance);
        financeRepository.save(finance);


        ResponseEntity<List<FinanceGetResponse>> response = restTemplate.exchange("api/v1/finances/by-user-id",
                HttpMethod.GET, regularUserHeader, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isEmpty();
        response.getBody().forEach(financeGetResponse -> assertThat(financeGetResponse.getUser().getId())
                .isNotEqualTo(savedUser.getId()));
    }

    @Test
    @DisplayName("findByUserId() returns 403 Forbidden when user does not use the correct token")
    void findById_Returns403Forbidden_WhenUserDoesNotUseTheCorrectToken() {
        ResponseEntity<List<FinanceGetResponse>> response = restTemplate.exchange("api/v1/finances/by-user-id",
                HttpMethod.GET, wrongHeader, new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("createFinance() returns 201 Created when successful")
    void createFinance_Returns201Created_WhenSuccessful() {
        var user = userRepository.findById(authenticatedRegularUserId).get();
        Finance finance = financeUtils.createValidFinance();
        finance.setUser(user);

        var financePostRequest = financeUtils.createValidFinancePostRequest();

        ResponseEntity<FinancePostResponse> response = restTemplate.exchange("api/v1/finances", HttpMethod.POST,
                new HttpEntity<>(financePostRequest, regularUserHeader.getHeaders()), FinancePostResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getUser().getId()).isEqualTo(userMapper.toUserPostResponse(user).getId());
    }

    @Test
    @DisplayName("createFinance() returns 201 Created with finance user equals to authenticated user when finance user is different from authenticated user")
    void createFinance_Returns201CreatedWithFinanceUserEqualsToAuthenticatedUser_WhenFinanceUserIsDifferentFromAuthenticatedUser() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);
        var finance = financeUtils.createValidFinance();
        finance.setUser(savedUser);
        var financePostRequest = financeUtils.createValidFinancePostRequest();

        ResponseEntity<FinancePostResponse> response = restTemplate.exchange("api/v1/finances", HttpMethod.POST,
                new HttpEntity<>(financePostRequest, regularUserHeader.getHeaders()), FinancePostResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUser().getId()).isNotEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("updateFinance() returns 204 No Content when successful")
    void updateFinance_Returns204NoContent_WhenSuccessful() {
        Finance partialFinance = financeUtils.createValidFinance();
        partialFinance.setUser(userRepository.findById(authenticatedRegularUserId).get());
        Finance financeToBeUpdated = financeRepository.save(partialFinance);
        var financePutRequest = financeUtils.createValidFinancePutRequest(financeToBeUpdated);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/finances", HttpMethod.PUT,
                new HttpEntity<>(financePutRequest, regularUserHeader.getHeaders()), Void.class);

        var updatedFinance = financeRepository.findById(financePutRequest.getId()).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(updatedFinance).isNotNull();
        assertThat(updatedFinance.getUser().getId()).isEqualTo(authenticatedRegularUserId);
        assertThat(updatedFinance.getName()).isEqualTo("Updated finance");
    }

    @Test
    @DisplayName("updateUser() returns 204 No Content with finance user equals to authenticated user when finance user is different from authenticated user")
    void updateFinance_Returns204NoContentWithFinanceUserEqualsToAuthenticatedUser_WhenFinanceUserIsDifferentFromAuthenticatedUser() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);

        Finance partialFinance = financeUtils.createValidFinance();
        partialFinance.setUser(userRepository.findById(savedUser.getId()).get());

        Finance financeToBeUpdated = financeRepository.save(partialFinance);

        var financePutRequest = financeUtils.createValidFinancePutRequest(financeToBeUpdated);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/finances", HttpMethod.PUT,
                new HttpEntity<>(financePutRequest, regularUserHeader.getHeaders()), Void.class);

        Finance updatedFinance = financeRepository.findById(financePutRequest.getId()).get();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(updatedFinance.getName()).isEqualTo("Updated finance");
        assertThat(updatedFinance.getUser().getId()).isEqualTo(authenticatedRegularUserId);
        assertThat(updatedFinance.getUser().getId()).isNotEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("delete() returns 204 No Content when successful")
    void delete_Returns204NoContent_WhenSuccessful() {
        Finance financeToBeSaved = financeUtils.createValidFinance();
        financeToBeSaved.setUser(userRepository.findById(authenticatedRegularUserId).get());

        var savedFinance = financeRepository.save(financeToBeSaved);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/finances/" + savedFinance.getId(),
                HttpMethod.DELETE, regularUserHeader, Void.class);

        Optional<Finance> deletedFinance = financeRepository.findById(savedFinance.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(deletedFinance).isEmpty();
    }

    @Test
    @DisplayName("delete() returns 403 Forbidden when user tries to delete finance from another user")
    void delete_Returns403Forbidden_WhenUserIsNotAdminAndTriesToDeleteAnotherUser() {
        var user = userUtils.createValidWithRandomEmailAndCpfUser();
        User savedUser = userRepository.save(user);
        var financeToBeSaved = financeUtils.createValidFinance();
        financeToBeSaved.setUser(savedUser);

        var savedFinance = financeRepository.save(financeToBeSaved);

        ResponseEntity<Void> response = restTemplate.exchange("api/v1/finances/" + savedFinance.getId(),
                HttpMethod.DELETE, regularUserHeader, Void.class);

        Optional<Finance> deletedFinance = financeRepository.findById(savedFinance.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(deletedFinance).isPresent();
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
