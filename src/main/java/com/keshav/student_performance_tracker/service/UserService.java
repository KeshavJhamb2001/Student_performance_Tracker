package com.keshav.student_performance_tracker.service;

import com.keshav.student_performance_tracker.model.User;
import com.keshav.student_performance_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Inject BCryptPasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updatePassword(User user, String newPassword) {
        String encoded = passwordEncoder.encode(newPassword);
        user.setPassword(encoded);
        userRepository.save(user);
    }

    public Optional<User> login(String email, String rawPassword) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String hashedPassword = user.get().getPassword();

            // Check if rawPassword matches the hashed password in DB
            if (passwordEncoder.matches(rawPassword, hashedPassword)) {
                return user; // Login successful
            }
        }

        return Optional.empty(); // Login failed
    }
}
