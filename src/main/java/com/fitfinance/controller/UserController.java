package com.fitfinance.controller;

import com.fitfinance.domain.User;
import com.fitfinance.mapper.UserMapper;
import com.fitfinance.request.UserPostRequest;
import com.fitfinance.request.UserPutRequest;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.response.UserPostResponse;
import com.fitfinance.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> findUserById(@PathVariable Long id) {
        var userFound = userService.findById(id);
        var response = mapper.toUserGetResponse(userFound);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> createUser(@RequestBody @Valid UserPostRequest userPostRequest) {
        var user = mapper.toUser(userPostRequest);
        user = userService.createUser(user);
        var response = mapper.toUserPostResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UserPostResponse> updateUser(@RequestBody @Valid UserPutRequest userPutRequest) {
        var userToUpdate = mapper.toUser(userPutRequest);
        userService.updateUser(userToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
