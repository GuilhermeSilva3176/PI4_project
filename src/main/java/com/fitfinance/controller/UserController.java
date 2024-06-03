package com.fitfinance.controller;

import com.fitfinance.domain.User;
import com.fitfinance.mapper.UserMapper;
import com.fitfinance.request.ChangePasswordRequest;
import com.fitfinance.request.UserPostRequest;
import com.fitfinance.request.UserPutRequest;
import com.fitfinance.response.UserGetResponse;
import com.fitfinance.response.UserPostResponse;
import com.fitfinance.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserGetResponse>> findAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserGetResponse> userGetResponses = mapper.toUserGetResponses(allUsers);
        return ResponseEntity.ok(userGetResponses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        var userFound = userService.findById(id);
        var response = mapper.toUserGetResponse(userFound);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserPostResponse> create(@RequestBody @Valid UserPostRequest userPostRequest) {
        var user = mapper.toUser(userPostRequest);
        user = userService.createUser(user);
        var response = mapper.toUserPostResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserPutRequest userPutRequest) {
        var userToUpdate = mapper.toUser(userPutRequest);
        userService.updateUser(userToUpdate, getUser());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        userService.changePassword(changePasswordRequest, connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id, getUser());
        return ResponseEntity.noContent().build();
    }

    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
