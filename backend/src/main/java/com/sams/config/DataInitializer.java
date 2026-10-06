package com.sams.config;

import com.sams.entity.User;
import com.sams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        updatePasswordIfPresent("admin", "Admin@123");
        updatePasswordIfPresent("hod", "Hod@123");
        updatePasswordIfPresent("faculty", "Faculty@123");
        updatePasswordIfPresent("student", "Student@123");

        for (int i = 2; i <= 10; i++) {
            updatePasswordIfPresent("faculty" + i, "Faculty@123");
            updatePasswordIfPresent("student" + i, "Student@123");
        }
    }

    private void updatePasswordIfPresent(String username, String rawPassword) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
        });
    }
}
