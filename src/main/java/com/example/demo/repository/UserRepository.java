package com.example.demo.repository;

import com.example.demo.domain.Company;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findUserById(long id);

    List<User> findAll();

    User findUserByEmail(String username);

    boolean existsByEmail(String email);

    User findByRefreshTokenAndEmail(String token, String email);

    List<User> findByCompany(Company company);

    Optional<User> findByEmail(String email);
}
