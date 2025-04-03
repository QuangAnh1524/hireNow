package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        return this.userRepository.save(user);
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User getUserById(long id) {
      return this.userRepository.findUserById(id);
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User updateUser(User user) {
        User currentUser = this.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            String passwordEncoded = passwordEncoder.encode(currentUser.getPassword());
            currentUser.setPassword(passwordEncoded);
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findUserByEmail(username);
    }
}
