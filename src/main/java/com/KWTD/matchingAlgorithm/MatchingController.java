package com.KWTD.matchingAlgorithm;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.KWTD.mentor.Mentor;

@RestController
public class MatchingController {
    
    /**
     * @see this route is made for testing data fetching
     * @return null
     */
    @GetMapping("/getMentors")
    public List<Mentor> getMentors(@RequestParam(value="", required=false) String clusterName){
        if(clusterName==null || clusterName==""){
            return new ClusterService().getMentors();
        }else{
            return new ClusterService().getClusterMentors(clusterName);
        }
    }

    
    /**
     * @return makes clusters for mentors
     * only used when need to reset all clusters
     * and in the start to create initial clusters
     */
    @GetMapping("/makeClusters")
    public String makeClusters(){
        try {
            return new ClusterService().formClusters();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error in forming clusters";
    }

    @GetMapping("/getMatch")
    public String getMatch(@RequestParam String menteeNumber){
        return new MatchingService().getMatch(menteeNumber);
    }


    @GetMapping("/test")
    public void getRes(){
        System.out.println("called");
    }
}
