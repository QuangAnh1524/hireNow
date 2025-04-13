package com.example.demo.service;

import com.example.demo.domain.DTO.Meta;
import com.example.demo.domain.DTO.ResultPaginationDTO;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {

    }

    public ResultPaginationDTO getAllUser(Specification<User> specification, Pageable pageable) {
        Page<User> users = this.userRepository.findAll(specification, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(users.getTotalPages());
        meta.setTotal(users.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(users.getContent());

        return resultPaginationDTO;
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
