package com.KWTD.mentee;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MenteeController {
    private static Firestore database = FirestoreClient.getFirestore();
    
    public static String addMentee(Mentee mentee){
        DocumentReference ref = database
                                    .collection("mentee_user")
                                    .document(mentee.getPhone());

        ApiFuture<WriteResult> result = ref.set(mentee);
        try{
            System.out.println("Update time: " + result.get().getUpdateTime());
            return result.get().getUpdateTime().toString();
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
            return e.toString();
        }
    }

    public static Mentee getMentee(String phoneNumber){
        Mentee mentee = new Mentee();

        DocumentReference ref = database
                                    .collection("mentee_user")
                                    .document(phoneNumber);
        try {
            DocumentSnapshot snapshot = ref.get().get();
            if(snapshot.exists()){
                mentee = snapshot.toObject(Mentee.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return mentee;
    }

    public static String updateMentee(Mentee mentee){
        DocumentReference ref = database
                                    .collection("mentee_user")
                                    .document(mentee.getPhone());

        ApiFuture<WriteResult> result = ref.set(mentee);
        try{
            System.out.println("Update time: " + result.get().getUpdateTime());
            return result.get().getUpdateTime().toString();
        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
            return e.toString();
        }
    }


}