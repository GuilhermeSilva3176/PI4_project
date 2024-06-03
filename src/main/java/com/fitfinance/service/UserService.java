package com.fitfinance.service;

import com.fitfinance.domain.User;
import com.fitfinance.exception.EmailAlreadyExistsException;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.TokenRepository;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.request.ChangePasswordRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public User createUser(User user) {
        assertEmailIsUnique(user.getEmail(), user.getId());
        return userRepository.save(user);
    }

    public void updateUser(User partialUserToUpdate, User user) {
        var savedUser = findById(partialUserToUpdate.getId());
        assertEmailIsUnique(partialUserToUpdate.getEmail(), partialUserToUpdate.getId());

        if (!savedUser.getId().equals(user.getId())) {
            throw new SecurityException("User " + user.getId() + " - " + user.getEmail() +
                    " does not have permission to update another users");
        }

        var password = partialUserToUpdate.getPassword() == null ? savedUser.getPassword() : partialUserToUpdate.getPassword();
        var roles = savedUser.getRoles();

        var userToUpdate = partialUserToUpdate.withRoles(roles).withPassword(password);

        userRepository.save(userToUpdate);
    }

    public void delete(Long id, User user) {
        var foundUser = findById(id);
        if (!foundUser.getId().equals(user.getId()) && !user.getRoles().contains("ADMIN")) {
            throw new SecurityException("User " + user.getId() + " - " + user.getEmail() +
                    " does not have permission to delete the user: " + foundUser.getId());
        }

        tokenRepository.deleteAllByUserId(foundUser.getId());
        userRepository.delete(foundUser);
        SecurityContextHolder.clearContext();
    }

    private void assertEmailIsUnique(String email, Long userId) {
        userRepository.findByEmail(email)
                .ifPresent(userFound -> {
                    if (!userFound.getId().equals(userId)) {
                        throw new EmailAlreadyExistsException("Email '%s' already in use".formatted(email));
                    }
                });
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Passwords does not match");
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalArgumentException("Passwords are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
