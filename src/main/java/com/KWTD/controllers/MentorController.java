package com.KWTD.mentor;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.ExecutionError;

@RestController
public class MentorController {

    public MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping("/addMentor")
    public String createMentor(@RequestBody Mentor mentor)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.createMentor(mentor, mentor.getPhone());
    }

    @GetMapping("/getMentor")
    public Mentor getMentor(@RequestParam String phone)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.getMentor(phone);
    }

    @PutMapping("/updateMentor")
    public String updateMENTOR(@RequestBody Mentor mentor)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.updateMentor(mentor, mentor.getPhone());
    }

    @PutMapping("/deleteMentor")
    public String deleteMentor(@RequestParam String phone) throws InterruptedException, ExecutionError {
        return mentorService.deleteMentor(phone);
    }

}
