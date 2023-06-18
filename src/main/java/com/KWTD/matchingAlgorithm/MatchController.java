package com.KWTD.matchingAlgorithm;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KWTD.mentor.Mentor;

@RestController
public class MatchController {
    
    /**
     * @see this route is made for testing data fetching
     * @return null
     */
    @GetMapping("/getMentors")
    public String getMentors(){

        List<Mentor> mentor = new MatchService().getMentors();
        System.out.println(mentor.get(0).getSkills().get(0));
        return "Hope it works";
    }
}
