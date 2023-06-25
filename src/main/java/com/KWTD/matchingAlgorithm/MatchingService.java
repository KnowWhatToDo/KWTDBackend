package com.KWTD.matchingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.KWTD.mentee.Mentee;
import com.KWTD.mentee.MenteeServices;
import com.KWTD.mentor.Mentor;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MatchingService {

    private static Firestore database = FirestoreClient.getFirestore();

    public Mentor getMatch(String menteeNumber){
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

        Mentor selectedMentor = new Mentor();

        for(int i=0;i<mentors.size();i++){
            numberOfMentees[i] = getNumberOfMentees(mentors.get(i).getPhone());
        }

        
        // if there are mentors with zero mentees
        boolean hasZero = false;
        int min;
        

        // basic implementation, points system to be added on top of this
        if(mentee.getCollegeYear()!="Working"){
            min = numberOfMentees[0];
            for(int i=0;i<mentors.size();i++){
                if(min < numberOfMentees[i]){
                    min = numberOfMentees[i];
                    selectedMentor = mentors.get(i);
                }
            }
        }else{
            min = numberOfMentees[numberOfMentees.length-1];
            for(int i=mentors.size()-1;i>=0;i++){
                if(min < numberOfMentees[i]){
                    min = numberOfMentees[i];
                    selectedMentor = mentors.get(i);
                }
            }
        }
        
        // updating details in mentee's profile
        List<String> selectedMentorUpdate = mentee.getMentors();
        selectedMentorUpdate.add(selectedMentor.getPhone());
        mentee.setMentors(selectedMentorUpdate);
        MenteeServices.updateMentee(mentee);


        // add match in database
        addMatch(selectedMentor.getPhone(), mentee.getPhone());

        return selectedMentor;
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return menteeList.size();
    }

    public void addMatch(String mentorNumber ,String menteeNumber){
       List<String> menteeList = new ArrayList<>(); 
        CollectionReference ref = database.collection("match");
        Map<String, Object> data;
        try {
            data = ref.document(mentorNumber).get().get().getData();
            if(data!=null){
                menteeList = (List<String>) data.get("menteeList");
            }

            menteeList.add(menteeNumber);
            Map<String, List<String>> updatedData = new HashMap<>();
            updatedData.put("menteeList", menteeList);
            ref.document(menteeNumber).set(updatedData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
