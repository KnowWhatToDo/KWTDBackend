package com.KWTD.matchingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.KWTD.mentor.Mentor;
import com.KWTD.mentor.MentorService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class ClusterService {
    
    Firestore database = FirestoreClient.getFirestore();
    
    /**
     * @return : {@code List<Mentor>} stores a list of all the mentors
     */
    public List<Mentor> getMentors(){
        CollectionReference ref = database.collection("mentor_user");
        ArrayList<Mentor> mentorList = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documentSnapshots = ref.get().get().getDocuments();
            for(int i=0;i<documentSnapshots.size();i++){
                mentorList.add(documentSnapshots.get(i).toObject(Mentor.class));
            }
        } catch (Exception e) {
            System.out.println("Error in getting Documents from collection: \"mentee_user\"");
            System.out.println("Refer Class: DataFetch.java");            
        }
        return mentorList;
    }

    public List<Mentor> getClusterMentors(String clusterName){
        List<String> mentorIDs = getClusterIds(clusterName);
        List<Mentor> mentors = new ArrayList<>();
        for(int i=0;i<mentorIDs.size();i++){
            try {
                mentors.add(new MentorService().getMentor(mentorIDs.get(i)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return mentors;
    }



    public List<String> getClusterIds(String domain){
        CollectionReference ref = database.collection("mentor_clusters");
        List<String> mentorReferences = new ArrayList<String>();
        try {
            Map<String, Object> mentorIDs = ref.document(domain).get().get().getData();
            mentorReferences = (List<String>) mentorIDs.get("mentor_ids");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return mentorReferences;
    }

    /**
     * <pre> <h2>
     * Forms clusters of mentors based on their skills
     * </h2> </pre>
     * @return (String)confirmation of cluster formation
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String formClusters() throws InterruptedException, ExecutionException{

        List<Mentor> mentors = getMentors();

        // eliminating unverified mentors
        for(int i=0;i<mentors.size();i++){
            if(!mentors.get(i).getIsVerified()){
                mentors.remove(i);
            }
        }

        // sorting mentors by their experience(in years) in ascending order
        Collections.sort(mentors);

        List<String> clusterNames = getClusterNames(mentors); 
        // for(int i=0;i<clusterNames.size();i++){
        //     System.out.println(clusterNames.get(i));
        // }

        

        // is a list of mentors 
        List<List<String>> mentorLists = new ArrayList<>();

        // initializing lists
        for(int i=0;i<clusterNames.size();i++){
            mentorLists.add(new ArrayList<String>());
        }

        // adding mentors to list
        for(int i=0;i<clusterNames.size();i++){
            for(Mentor mentor:mentors){
                if(mentor.getSkills().contains(clusterNames.get(i))){
                    mentorLists.get(i).add(mentor.getPhone());
                }
            }
        }

        // System.out.println(mentorLists);

        CollectionReference mentorClusterCollection = database
                                            .collection("mentor_clusters");
        
        // will store the key value data stored in the document
        Map<String, List<String>> clusterData = new HashMap<>();

        for(int i=0;i<clusterNames.size();i++){
            
            clusterData.put("mentor_ids", mentorLists.get(i));

            ApiFuture<WriteResult> future = mentorClusterCollection
                                            .document(clusterNames.get(i))
                                            .set(clusterData);

            WriteResult result = future.get();
            clusterData.clear();
            System.out.println("Write data at: "+ result.getUpdateTime());
        }

        return "Formed Clusters";
    }

   /**
    * <pre> <h2>
     * Gets all uniquely possible cluster names from mentor data
     * 
     * </h2> </pre>
     * @param mentors : {@code List<Mentor>}
     * @return {@code List<String>} which stores all unique cluster names
     */
     public List<String> getClusterNames(List<Mentor> mentors){
        ArrayList<String> clusterNames = new ArrayList<>();
        HashSet<String> uniqueDomains = new HashSet<>();
        
        for(int i=0;i<mentors.size();i++){
            for(int j=0;j<mentors.get(i).getSkills().size();j++){
                uniqueDomains.add(mentors.get(i).getSkills().get(j));
            }
        }
        clusterNames.clear();
        clusterNames.addAll(uniqueDomains);    
        return clusterNames;
    }

}
