package com.KWTD.mentoring;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentoring")
public class MentoringController {

    private final MentoringService mentoringService;

    @Autowired
    public MentoringController(MentoringService mentoringService) {
        this.mentoringService = mentoringService;
    }

    @PostMapping("/create")
    public String createMentoring(@RequestBody Mentoring mentoring) throws ExecutionException, InterruptedException {
        return mentoringService.createMENTORING(mentoring);
    }

    @GetMapping("/get/{mentor}")
    public Mentoring getMentoringByMentor(@PathVariable String mentor) throws ExecutionException, InterruptedException {
        return mentoringService.getMENTORINGByMentor(mentor);
    }

    @PutMapping("/update")
    public String updateMentoring(@RequestBody Mentoring mentoring) throws ExecutionException, InterruptedException {
        return mentoringService.updateMENTORING(mentoring);
    }

    @DeleteMapping("/delete/{mentor}")
    public String deleteMentoring(@PathVariable String mentor) throws ExecutionException, InterruptedException {
        return mentoringService.deleteMENTORING(mentor);
    }
}
