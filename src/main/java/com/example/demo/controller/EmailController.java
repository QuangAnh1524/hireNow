package com.example.demo.controller;

import com.example.demo.domain.Job;
import com.example.demo.domain.Skill;
import com.example.demo.domain.response.email.SkillSubscriptionEmailDTO;
import com.example.demo.repository.JobRepository;
import com.example.demo.service.EmailService;
import com.example.demo.util.annotation.ApiMessage;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private final EmailService emailService;
    private final JobRepository jobRepository;

    public EmailController(EmailService emailService, JobRepository jobRepository) {
        this.emailService = emailService;
        this.jobRepository = jobRepository;
    }

    @GetMapping("/email")
    @ApiMessage("Send skill subscription email")
    public String sendSimpleEmail() throws MessagingException {
        // Tạo dữ liệu mẫu cho SkillSubscriptionEmailDTO
        SkillSubscriptionEmailDTO emailDTO = new SkillSubscriptionEmailDTO();
        emailDTO.setCandidateName("Quang Anh");
        emailDTO.setSkillNames("Java, Python");
        emailDTO.setSubscriptionDate(Instant.now());
        emailDTO.setContactEmail("hr@company.com");
        emailDTO.setJobPortalUrl("https://www.company.com/jobs");
        emailDTO.setCompanyWebsite("https://www.company.com");
        emailDTO.setUnsubscribeUrl("https://www.company.com/unsubscribe?email=ninoel2004@gmail.com");

        // Lấy danh sách Job từ JobRepository (ví dụ: tìm Job có kỹ năng "Java")
        List<Job> jobs = jobRepository.findBySkillsIn(
                List.of(new Skill() {{
                    setId(1);
                    setName("Java");
                }})
        );

        // Chuyển Job thành JobInfo
        List<SkillSubscriptionEmailDTO.JobInfo> jobInfos = jobs.stream()
                .map(job -> {
                    SkillSubscriptionEmailDTO.JobInfo jobInfo = new SkillSubscriptionEmailDTO.JobInfo();
                    jobInfo.setName(job.getName());
                    jobInfo.setLocation(job.getLocation());
                    jobInfo.setLevel(job.getLevel().name());
                    jobInfo.setSalary(job.getSalary());
                    return jobInfo;
                })
                .collect(Collectors.toList());

        emailDTO.setJobList(jobInfos);

        // Gửi email
        this.emailService.sendSkillSubscriptionEmail(
                "ninoel2004@gmail.com",
                "Test Skill Subscription Email",
                emailDTO
        );

        return "ok";
    }
}