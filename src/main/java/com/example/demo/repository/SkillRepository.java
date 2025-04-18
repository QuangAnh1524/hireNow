package com.example.demo.repository;

import com.example.demo.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

    Optional<Skill> findByName(String name);

    boolean existsByName(String name);

    Skill save(Skill skill);

    Optional<Skill> getSkillById(long id);

    Page<Skill> findAll(Specification<Skill> specification, Pageable pageable);

    void delete(Skill skill);

    List<Skill> findByIdIn(List<Long> skills);
}
