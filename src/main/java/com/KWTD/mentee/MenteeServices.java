package com.KWTD.mentee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenteeServices {

    @PostMapping("/addMentee")
    public String createMentee(@RequestBody Mentee mentee){
        return MenteeController.addMentee(mentee);
    }
    
    @GetMapping("/getMentee")
    public Mentee getMentee(@RequestParam String phone){
        Mentee mentee = MenteeController.getMentee(phone);
        return mentee;
    }
}
