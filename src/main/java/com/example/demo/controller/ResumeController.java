package com.example.demo.controller;

import com.example.demo.domain.Resume;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.domain.response.resume.ResCreateResumeDTO;
import com.example.demo.domain.response.resume.ResFetchResumeDTO;
import com.example.demo.domain.response.resume.ResUpdateResumeDTO;
import com.example.demo.service.ResumeService;
import com.example.demo.service.exception.idInvalidException;
import com.example.demo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resumes")
    @ApiMessage("Create a resume")
    public ResponseEntity<ResCreateResumeDTO> createResume(@Valid @RequestBody Resume resume) throws idInvalidException {
        //check id exists
        boolean isIdExist = this.resumeService.checkResumeExistByUserAndJob(resume);
        if (!isIdExist) {
            throw new idInvalidException("User id hoặc Job id không tồn tại");
        }
        //create new resume
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.create(resume));
    }

    @PutMapping("/resumes")
    @ApiMessage("Update a resume")
    public ResponseEntity<ResUpdateResumeDTO> updateResume(@RequestBody Resume resume) throws idInvalidException {
        //check id exist
        Optional<Resume> resumeOptional = this.resumeService.getById(resume.getId());
        if (resumeOptional.isEmpty()) {
            throw new idInvalidException("Resume với id = " + resume.getId() + " không tồn tại");
        }

        Resume resume1 = resumeOptional.get();
        resume1.setStatus(resume.getStatus());

        return ResponseEntity.ok().body(this.resumeService.update(resume1));
    }

    @DeleteMapping("/resumes/{id}")
    @ApiMessage("Delete a resume by id")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") long id) throws idInvalidException {
        //check id exist
        Optional<Resume> resumeOptional = this.resumeService.getById(id);
        if (resumeOptional.isEmpty()) {
            throw new idInvalidException("Resume với id = " + id + " không tồn tại");
        }

        this.resumeService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/resumes/{id}")
    @ApiMessage("Fetch a resume by id")
    public ResponseEntity<ResFetchResumeDTO> getResumeById(@PathVariable("id") long id) throws idInvalidException {
        //check id exist
        Optional<Resume> resumeOptional = this.resumeService.getById(id);
        if (resumeOptional.isEmpty()) {
            throw new idInvalidException("Resume với id = " + id + " không tồn tại");
        }
        return ResponseEntity.ok().body(this.resumeService.getResume(resumeOptional.get()));
    }

    @GetMapping("/resumes")
    @ApiMessage("Fetch all resume with paginate")
    public ResponseEntity<ResultPaginationDTO> getAll(@Filter Specification<Resume> spec, Pageable pageable) {
        return ResponseEntity.ok().body(this.resumeService.getAllResume(spec, pageable));
    }
}
