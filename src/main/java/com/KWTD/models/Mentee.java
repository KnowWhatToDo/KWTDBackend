package com.KWTD.mentee;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mentee {
    private String name;
    private String phone;
    private String email;
    private String collegeName;
    private String collegeYear;
    private String collegeBranch;
    private String LinkedInProfile;
    private List<String> questions;
    private List<String> answers;
    private List<String> mentors;
    private List<String> meetings;
}