package com.KWTD.register.mentee;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.KWTD.register.mentor.mentor;

@Getter
@Setter
public class mentee {

    private String name;
    private String phone;
    private String email;
    // private String Objective;
    private String collegeName;
    private String collegeYear;
    private String collegeBranch;
    private String LinkedInProfile;

    private List<String> questions;
    private List<String> answers;
    private List<String> mentors;

}
