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
        return "Test for fetching mentors";
    }

    
    /**
     * @return makes clusters for mentors
     * only used when need to reset all clusters
     * and in the start to create initial clusters
     */
    @GetMapping("/makeClusters")
    public String makeClusters(){
        try {
            return new MatchService().formClusters();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error in forming clusters";
    }
}
