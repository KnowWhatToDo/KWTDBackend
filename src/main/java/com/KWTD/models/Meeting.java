package com.KWTD.meeting;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Meeting {
    private String meetingId;
    private String mentorNumber;
    private String menteeNumber;
    private String meetingLink;
    private String meetingDate;
    private String meetingTime;

    @Override
    public boolean equals(Object obj){
        if(obj==null){
            return false;
        }

        if(obj.getClass()!=this.getClass()){
            return false;
        }

        Meeting meeting = (Meeting) obj;

        if(meeting.getMeetingId().equals(this.meetingId)){
            return true;
        }else{
            return false;
        }
    }
}
