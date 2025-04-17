package com.example.demo.service;

import com.example.demo.domain.Skill;
import com.example.demo.domain.Subscriber;
import com.example.demo.repository.SkillRepository;
import com.example.demo.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
    }

    public boolean isExistByEmail(String email) {
        return this.subscriberRepository.existsByEmail(email);
    }

    public Subscriber create(Subscriber subscriber) {
        //check skill
        if (subscriber.getSkills() != null) {
            List<Long> skills = subscriber.getSkills().stream().map(Skill::getId).toList();

            List<Skill> dbSkills = this.skillRepository.findByIdIn(skills);
            subscriber.setSkills(dbSkills);
        }
        return this.subscriberRepository.save(subscriber);
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
