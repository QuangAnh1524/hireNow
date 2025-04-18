package com.example.demo.domain.response.email;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class SkillSubscriptionEmailDTO {
    private String candidateName;
    private String skillNames;
    private List<JobInfo> jobList;
    private Instant subscriptionDate;
    private String contactEmail;
    private String jobPortalUrl;
    private String companyWebsite;
    private String unsubscribeUrl;

    @Getter
    @Setter
    public static class JobInfo {
        private String name;
        private String location;
        private String level;
        private double salary;
    }
}