package com.KWTD.meeting;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.*;

import com.google.common.util.concurrent.ExecutionError;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    public MeetingService meetingServices;

    public MeetingController(MeetingService meetingServices) {
        this.meetingServices = meetingServices;
    }

    @PostMapping("/addmeeting")
    public String createMeeting(@RequestBody Meeting meeting)
            throws InterruptedException, ExecutionError, ExecutionException {
        return meetingServices.createMeeting(meeting, meeting.getMentorNumber());
    }

    @GetMapping("/getmeeting")
    public Meeting getMeeting(@RequestParam("mentorNumber") String mentorNumber)
            throws InterruptedException, ExecutionError, ExecutionException {
        return meetingServices.getMeeting(mentorNumber);
    }

    @PutMapping("/updatemeeting")
    public String updateMeeting(@RequestBody Meeting meeting)
            throws InterruptedException, ExecutionError, ExecutionException {
        return meetingServices.updateMeeting(meeting, meeting.getMentorNumber());
    }

    @DeleteMapping("/deletemeeting")
    public String deleteMeeting(@RequestParam("mentorNumber") String mentorNumber)
            throws InterruptedException, ExecutionError {
        return meetingServices.deleteMeeting(mentorNumber);
    }
}
