package com.example.demo.controller;

import com.example.demo.domain.DTO.ResCreateUserDTO;
import com.example.demo.domain.DTO.ResUpdateUserDTO;
import com.example.demo.domain.DTO.ResUserDTO;
import com.example.demo.domain.DTO.ResultPaginationDTO;
import com.example.demo.domain.RestReponse;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.service.exception.idInvalidException;
import com.example.demo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter Specification<User> specification, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUser(specification, pageable));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Get user By Id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws idInvalidException {
        User user = this.userService.getUserById(id);
        if (user == null) {
            throw new idInvalidException("User với id = " + id + " không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(user));
    }

    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User user) throws idInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(user.getEmail());
        if (isEmailExist) {
            throw new idInvalidException(
                    "Email" + user.getEmail() + "đã tồn tại, vui lòng sử dụng email khác"
            );
        }
        User user1 = this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(user1));
    }


    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<RestReponse<String>> deleteUser(@PathVariable("id") long id) throws idInvalidException {
        User currentUser = this.userService.getUserById(id);
        if (currentUser == null) {
            throw new idInvalidException("User với id = " + id + " không tồn tại");
        }
        this.userService.deleteUser(id);

        RestReponse<String> response = new RestReponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Delete success");
        response.setError(null);
        response.setData(null);

        return ResponseEntity.ok(response);
    }

    //update user
    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws idInvalidException {
        User user1 = this.userService.updateUser(user);
        if (user1 == null) {
            throw new idInvalidException("User với id = " + user.getId() + " không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(user1));
    }
}
