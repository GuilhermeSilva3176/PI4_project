package com.fitfinance.service;

import com.fitfinance.domain.User;
import com.fitfinance.exception.EmailAlreadyExistsException;
import com.fitfinance.exception.NotFoundException;
import com.fitfinance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

    public void updateUser(User partialUserToUpdate) {
        var savedUser = findById(partialUserToUpdate.getId());
        assertEmailIsUnique(partialUserToUpdate.getEmail(), partialUserToUpdate.getId());

        var password = partialUserToUpdate.getPassword() == null ? savedUser.getPassword() : partialUserToUpdate.getPassword();
        var roles = savedUser.getRoles();

        var userToUpdate = partialUserToUpdate.withRoles(roles).withPassword(password);

        userRepository.save(userToUpdate);
    }

    public void delete(Long id) {
        var user = findById(id);
        userRepository.delete(user);
    }

    private void assertEmailIsUnique(String email, Long userId) {
        userRepository.findByEmail(email)
                .ifPresent(userFound -> {
                    if (!userFound.getId().equals(userId)) {
                        throw new EmailAlreadyExistsException("Email '%s' already in use".formatted(email));
                    }
                });
    }
}
