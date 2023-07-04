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

    @PostMapping("/addMeeting")
    public String createMeeting(@RequestBody Meeting meeting)
            throws InterruptedException, ExecutionError, ExecutionException {
        return meetingServices.createMeeting(meeting);
    }

}
