package com.example.demo.controller;

import com.example.demo.domain.Job;
import com.example.demo.domain.Skill;
import com.example.demo.domain.Subscriber;
import com.example.demo.domain.response.email.SkillSubscriptionEmailDTO;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.SubscriberRepository;
import com.example.demo.service.EmailService;
import com.example.demo.util.annotation.ApiMessage;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.exceptions.TemplateInputException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private final EmailService emailService;
    private final JobRepository jobRepository;
    private final SubscriberRepository subscriberRepository;

    public EmailController(EmailService emailService, JobRepository jobRepository,
                           SubscriberRepository subscriberRepository) {
        this.emailService = emailService;
        this.jobRepository = jobRepository;
        this.subscriberRepository = subscriberRepository;
    }

    @GetMapping("/email")
    @ApiMessage("Send skill subscription email")
    @Scheduled(cron = "0 0 8 * * ?") //8h sáng mỗi ngày
    @Transactional
    public String sendSimpleEmail() throws MessagingException {
        try {
            List<Subscriber> subscribers = subscriberRepository.findAll();
            if (subscribers.isEmpty()) {
                return "No subscribers found";
            }

            for (Subscriber subscriber : subscribers) {
                SkillSubscriptionEmailDTO emailDTO = new SkillSubscriptionEmailDTO();
                emailDTO.setCandidateName(subscriber.getName());
                emailDTO.setSkillNames(subscriber.getSkills().stream().map(Skill::getName)
                        .collect(Collectors.joining(", ")));
                emailDTO.setSubscriptionDate(subscriber.getCreatedAt() != null ? subscriber.getCreatedAt() : Instant.now());
                emailDTO.setContactEmail("hr@company.com");
                emailDTO.setJobPortalUrl("https://www.company.com/jobs");
                emailDTO.setCompanyWebsite("https://www.company.com");
                emailDTO.setUnsubscribeUrl("https://www.company.com/unsubscribe?email=" + subscriber.getEmail());

                List<Job> jobs = jobRepository.findBySkillsIn(subscriber.getSkills());

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

                this.emailService.sendSkillSubscriptionEmail(subscriber.getEmail(),
                        "Skill Subscription Confirmation",
                        emailDTO
                );
            }

            return "Emails sent to all subscribers";
        } catch (TemplateInputException e) {
            throw new RuntimeException("Template not found: 'skill-subscription-confirmation'. Check src/main/resources/templates/", e);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
}