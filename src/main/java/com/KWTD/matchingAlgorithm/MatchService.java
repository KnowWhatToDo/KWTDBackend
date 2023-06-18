package com.KWTD.matchingAlgorithm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.KWTD.mentor.Mentor;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class MatchService {
    
    Firestore database = FirestoreClient.getFirestore();
    
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

}
