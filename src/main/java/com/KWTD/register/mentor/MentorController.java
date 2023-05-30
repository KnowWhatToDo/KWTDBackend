package com.KWTD.register.mentor;

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

    @PostMapping("/addmentor")
    public String createMENTOR(@RequestBody mentor mentor)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.createMENTOR(mentor, mentor.getPhone());
    }

    @GetMapping("/getmentor")
    public mentor getMENTOR(@RequestParam String phone)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.getMENTOR(phone);
    }

    @PutMapping("/updatementor")
    public String updateMENTOR(@RequestBody mentor mentor)
            throws InterruptedException, ExecutionError, ExecutionException {
        return mentorService.updateMENTOR(mentor, mentor.getPhone());
    }

    @PutMapping("/deletementor")
    public String deleteMENTOR(@RequestParam String phone) throws InterruptedException, ExecutionError {
        return mentorService.deleteMENTOR(phone);
    }

}
