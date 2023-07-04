package com.KWTD.mentor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Mentor implements Comparable<Mentor> {
    private String phone;
    private String name;
    private String collegeName;
    private List<String> skills;
    private String email;
    private List<String> meetings;
    private String linkedinUrl;
    private float experience;
    private Boolean isVerified;
    
    @Override
    public int compareTo(Mentor mentor) {
        return Math.round(this.experience) - Math.round(mentor.getExperience());
    }
}