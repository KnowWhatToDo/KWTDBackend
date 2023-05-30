package com.KWTD.meeting;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Meeting {

    private String mentorNumber;
    private String menteeNumber;
    private String meetingLink;
    private String meetingDate;
    private String localTime;

}
