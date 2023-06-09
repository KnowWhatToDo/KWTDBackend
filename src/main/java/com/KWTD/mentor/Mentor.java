package com.KWTD.mentor;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Mentor {
    // Other properties

    private String phone;
    private String name;
    private String collegeName;
    private List<String> skills;
    private String email;
    private String linkedin_url;
    private Boolean is_verified;
    private List<Experience> experiences;
    // private long wallet;

}
