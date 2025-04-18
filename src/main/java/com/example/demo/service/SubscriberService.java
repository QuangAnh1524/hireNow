package com.example.demo.service;

import com.example.demo.domain.Job;
import com.example.demo.domain.Skill;
import com.example.demo.domain.Subscriber;
import com.example.demo.domain.response.email.SkillSubscriptionEmailDTO;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.repository.SubscriberRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository, JobRepository jobRepository, EmailService emailService) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    public boolean isExistByEmail(String email) {
        return this.subscriberRepository.existsByEmail(email);
    }

    @Transactional(rollbackFor = MessagingException.class)
    public Subscriber create(Subscriber subscriber) throws MessagingException {
        // Check skill
        if (subscriber.getSkills() != null && !subscriber.getSkills().isEmpty()) {
            List<Long> skillIds = subscriber.getSkills().stream().map(Skill::getId).toList();
            List<Skill> dbSkills = this.skillRepository.findByIdIn(skillIds);
            subscriber.setSkills(dbSkills);
        }

        // Lưu Subscriber
        Subscriber savedSubscriber = this.subscriberRepository.save(subscriber);

        // Tìm các Job phù hợp với skills
        List<Job> jobList = subscriber.getSkills() != null && !subscriber.getSkills().isEmpty()
                ? this.jobRepository.findBySkillsIn(subscriber.getSkills())
                : List.of();

        // Tạo DTO cho email
        SkillSubscriptionEmailDTO emailDTO = new SkillSubscriptionEmailDTO();
        emailDTO.setCandidateName(subscriber.getName());
        emailDTO.setSkillNames(subscriber.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.joining(", ")));
        emailDTO.setJobList(jobList.stream()
                .map(job -> {
                    SkillSubscriptionEmailDTO.JobInfo jobInfo = new SkillSubscriptionEmailDTO.JobInfo();
                    jobInfo.setName(job.getName());
                    jobInfo.setLocation(job.getLocation());
                    jobInfo.setLevel(job.getLevel().name());
                    jobInfo.setSalary(job.getSalary());
                    return jobInfo;
                })
                .collect(Collectors.toList()));
        emailDTO.setSubscriptionDate(savedSubscriber.getCreatedAt());
        emailDTO.setContactEmail("hr@company.com");
        emailDTO.setJobPortalUrl("https://www.company.com/jobs");
        emailDTO.setCompanyWebsite("https://www.company.com");
        emailDTO.setUnsubscribeUrl("https://www.company.com/unsubscribe?email=" + subscriber.getEmail());

        // Gửi email
        this.emailService.sendSkillSubscriptionEmail(
                subscriber.getEmail(),
                "Cơ hội việc làm đang chờ đón bạn, khám phá ngay",
                emailDTO
        );

        return savedSubscriber;
    }

    public Subscriber update(Subscriber subscriberDB, Subscriber subscriber) {
        if (subscriber.getSkills() != null) {
            List<Long> skillIds = subscriber.getSkills().stream()
                    .map(Skill::getId)
                    .toList();
            List<Skill> dbSkills = this.skillRepository.findByIdIn(skillIds);
            subscriberDB.setSkills(dbSkills);
        }
        return this.subscriberRepository.save(subscriberDB);
    }

    public Subscriber findById(long id) {
        return this.subscriberRepository.findById(id);
    }
}