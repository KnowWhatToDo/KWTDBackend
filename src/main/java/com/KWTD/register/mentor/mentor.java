package com.KWTD.register.mentor;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.time.LocalDate;

@Getter
@Setter
public class mentor {
    // Other properties

    private String phone;
    private String name;
    private String collegeName;
    private List<String> skills;
    private String email;
    private String linkedin_url;
    private Boolean is_verified;
    private List<Experience> experiences;
    private long wallet;

}
