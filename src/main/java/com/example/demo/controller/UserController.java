package com.example.demo.controller;

import com.example.demo.domain.DTO.ResultPaginationDTO;
import com.example.demo.domain.RestReponse;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.service.exception.idInvalidException;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter Specification<User> specification, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUser(specification, pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = this.userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<RestReponse<String>> deleteUser(@PathVariable("id") long id) throws idInvalidException {
        this.userService.deleteUser(id);

        RestReponse<String> response = new RestReponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Delete success");
        response.setError(null);
        response.setData(null);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User user1 = this.userService.updateUser(user);
        return ResponseEntity.ok(user1);
    }
}
