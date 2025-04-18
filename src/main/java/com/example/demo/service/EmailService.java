package com.example.demo.service;

import com.example.demo.domain.Job;
import com.example.demo.domain.response.email.SkillSubscriptionEmailDTO;
import com.example.demo.repository.JobRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailService {

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JobRepository jobRepository;

    public EmailService(MailSender mailSender, JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, JobRepository jobRepository) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.jobRepository = jobRepository;
    }

    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ninoel2004@gmail.com");
        message.setSubject("Tesing from Spring boot");
        message.setText("Hello world from spring boot email");
        this.mailSender.send(message);
    }

    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) throws MailException, MessagingException {
        //prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, isHtml);
        this.javaMailSender.send(mimeMessage);
    }

    public void sendEmailFromTemplateSync(String to, String subject, String templateName, String username, Object value) throws MessagingException {
        Context context = new Context();
        List<Job> jobs = this.jobRepository.findAll();
        String content = templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }

    public void sendSkillSubscriptionEmail(String to, String subject, SkillSubscriptionEmailDTO emailDTO) throws MessagingException {
        System.out.println(">>> Sending email to: " + to + " with template: skill-subscription-confirmation");
        Context context = new Context();
        context.setVariable("candidateName", emailDTO.getCandidateName());
        context.setVariable("skillNames", emailDTO.getSkillNames());
        context.setVariable("jobList", emailDTO.getJobList());
        context.setVariable("subscriptionDate", emailDTO.getSubscriptionDate());
        context.setVariable("contactEmail", emailDTO.getContactEmail());
        context.setVariable("jobPortalUrl", emailDTO.getJobPortalUrl());
        context.setVariable("companyWebsite", emailDTO.getCompanyWebsite());
        context.setVariable("unsubscribeUrl", emailDTO.getUnsubscribeUrl());

        String content = templateEngine.process("skill-subscription-confirmation", context);
        this.sendEmailSync(to, subject, content, false, true);
    }
}
