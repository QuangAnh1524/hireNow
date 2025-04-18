package com.example.demo.repository;

import com.example.demo.domain.Job;
import com.example.demo.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Optional<Job> findById(Long id);

    Page<Job> findAll(Specification<Job> specification, Pageable pageable);

    @Query("SELECT j FROM Job j JOIN j.skillList s WHERE s IN :skills")
    List<Job> findBySkillsIn(List<Skill> skills);
}
