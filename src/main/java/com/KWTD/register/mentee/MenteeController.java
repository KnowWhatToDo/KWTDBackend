package com.KWTD.register.mentee;

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
public class MenteeController {
    public MenteeServices menteeService;

    public MenteeController(MenteeServices menteeService) {
        this.menteeService = menteeService;
    }

    @PostMapping("/addmentee")
    public String createMENTEE(@RequestBody mentee mentee)
            throws InterruptedException, ExecutionError, ExecutionException {
        return menteeService.createMENTEE(mentee, mentee.getPhone());
    }

    @GetMapping("/getmentee")
    public mentee getMENTEE(@RequestParam String mentee_phone)
            throws InterruptedException, ExecutionError, ExecutionException {
        return menteeService.getMENTEE(mentee_phone);
    }

    @PutMapping("/updatementee")
    public String updateMENTEE(@RequestBody mentee mentee)
            throws InterruptedException, ExecutionError, ExecutionException {
        return menteeService.updateMENTEE(mentee, mentee.getPhone());
    }

    @PutMapping("/deletementee")
    public String deleteMENTEE(@RequestParam String mentee_phone) throws InterruptedException, ExecutionError {
        return menteeService.deleteMENTEE(mentee_phone);
    }

    @GetMapping("/testmentee")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

}
