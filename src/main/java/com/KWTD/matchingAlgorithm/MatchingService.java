package com.KWTD.matchingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.KWTD.mentee.Mentee;
import com.KWTD.mentee.MenteeServices;
import com.KWTD.mentor.Mentor;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MatchingService {
    private static Firestore database = FirestoreClient.getFirestore();
    public String getMatch(String menteeNumber){
        Mentee mentee = MenteeServices.getMentee(menteeNumber);

        String [] domains = mentee.getAnswers().get(0).split(",");
        
        String clusterDomain = "";

        CollectionReference clusterCollectionReference = database.collection("mentor_clusters");
        for(int i=0;i<domains.length;i++){
            if(clusterCollectionReference.document(domains[i].trim())!=null){
                clusterDomain = domains[i].trim();
                break;
            }
        }
        
        List<Mentor> mentors = new ClusterService().getClusterMentors(clusterDomain);
        

        int [] numberOfMentees = new int[mentors.size()];
        

        // sorting in ascending order by experience of the mentor
        Collections.sort(mentors);

        for(int i=0;i<mentors.size();i++){
            System.out.println(getNumberOfMentees(mentors.get(i).getPhone()));
        }

        return clusterDomain;
    }

    public int getNumberOfMentees(String mentorNumber){
        List<String> menteeList = new ArrayList<>();
        CollectionReference ref = database.collection("mentoring");
        if(ref.document(mentorNumber)==null){
            return 0;
        }else{
            try {
                Map<String, Object> data = ref.document(mentorNumber).get().get().getData();
                if(data!=null){
                    menteeList = (List<String>) data.get("menteeList");
                }
                System.out.println("working");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                
                e.printStackTrace();
            }
        }
        return menteeList.size();
    }

    public void addMentees(String mentorNumber ,String menteeNumber){
        CollectionReference ref = database.collection("mentoring");
        
    }
}
