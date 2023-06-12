package com.KWTD.mentor;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Mentor {
    private String phone;
    private String name;
    private String collegeName;
    private List<String> skills;
    private String email;
    private String linkedinUrl;
    private float experience;
    private Boolean isVerified;
}