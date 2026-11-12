package com.bitterjava.service;

import com.bitterjava.model.User;
import com.bitterjava.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z0-9_]{3,30}");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String dummyPasswordHash;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        // Keep unknown-user login attempts close in cost to known-user attempts.
        this.dummyPasswordHash = passwordEncoder.encode(UUID.randomUUID().toString());
    }

    public User register(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        if (!USERNAME_PATTERN.matcher(normalizedUsername).matches()) {
            throw new IllegalArgumentException("Use 3-30 letters, numbers, or underscores for your username.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Your password must be 8-72 UTF-8 bytes long.");
        }
        if (userRepository.findByUsername(normalizedUsername).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        String passwordHash = passwordEncoder.encode(password);
        try {
            return userRepository.insert(normalizedUsername, passwordHash);
        } catch (DuplicateKeyException exception) {
            throw new UsernameAlreadyExistsException();
        }
    }

    public Optional<User> authenticate(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        String rawPassword = password == null ? "" : password;
        Optional<User> user = userRepository.findByUsername(normalizedUsername);

        if (user.isEmpty()) {
            passwordEncoder.matches(rawPassword, dummyPasswordHash);
            return Optional.empty();
        }
        return passwordEncoder.matches(rawPassword, user.get().passwordHash())
                ? user
                : Optional.empty();
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        int byteLength = password.getBytes(StandardCharsets.UTF_8).length;
        return byteLength >= 8 && byteLength <= 72;
    }
}
