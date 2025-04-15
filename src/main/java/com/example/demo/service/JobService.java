package com.example.demo.service;

import com.example.demo.domain.Job;
import com.example.demo.domain.Skill;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.domain.response.job.ResCreateJobDTO;
import com.example.demo.domain.response.job.ResUpdateJobDTO;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    public ResCreateJobDTO create(Job job) {
        //check skills
        if (job.getSkillList() != null) {
            List<Long> reqSkills = job.getSkillList().stream().map(Skill::getId).toList();

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkillList(dbSkills);
        }
        //create job
        Job currentJob = this.jobRepository.save(job);

        // convert response
        ResCreateJobDTO dto = new ResCreateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());
        dto.setActive(currentJob.isActive());
        dto.setCreatedAt(currentJob.getCreatedAt());
        dto.setCreatedBy(currentJob.getCreatedBy());

        if (currentJob.getSkillList() != null) {
            List<String> skills = currentJob.getSkillList().stream().map(Skill::getName).toList();
            dto.setSkills(skills);
        }
        return dto;
    }

    public ResUpdateJobDTO update(Job job) {
        //check skills
        if (job.getSkillList() != null) {
            List<Long> reqSkills = job.getSkillList().stream().map(Skill::getId).toList();

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkillList(dbSkills);
        }

        //update job
        Job currentJob = this.jobRepository.save(job);

        //convert response
        ResUpdateJobDTO dto = new ResUpdateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());

        if (currentJob.getSkillList() != null) {
            List<String> skillNames = currentJob.getSkillList()
                    .stream()
                    .map(Skill::getName)
                    .toList();
            dto.setSkills(skillNames);
        }
        return dto;
    }

    public Optional<Job> getJobById(long id) {
        return this.jobRepository.getJobById(id);
    }

    public void delete(long id) {
        this.jobRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAll(Specification<Job> specification, Pageable pageable) {
        Page<Job> jobPage = this.jobRepository.findAll(specification, pageable);

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(jobPage.getTotalPages());
        meta.setTotal(jobPage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(jobPage.getContent());

        return resultPaginationDTO;
    }
}
