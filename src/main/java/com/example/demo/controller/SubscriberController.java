package com.example.demo.controller;

import com.example.demo.domain.Subscriber;
import com.example.demo.service.SubscriberService;
import com.example.demo.service.exception.idInvalidException;
import com.example.demo.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a subscriber")
    public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber subscriber) throws idInvalidException {
        //check email
        boolean isExist = this.subscriberService.isExistByEmail(subscriber.getEmail());
        if (isExist) {
            throw new idInvalidException("Email " + subscriber.getEmail() + " đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.create(subscriber));
    }

    @PutMapping("/subscribers")
    @ApiMessage("Update a subscriber")
    public ResponseEntity<Subscriber> update(@RequestBody Subscriber subscriber) throws idInvalidException {
        //check id
        Subscriber subscriberDB = this.subscriberService.findById(subscriber.getId());
        if (subscriberDB==null) {
            throw new idInvalidException("Id " + subscriber.getId() + " không tồn tại");
        }
        return ResponseEntity.ok().body(this.subscriberService.update(subscriberDB, subscriber));
    }
}
