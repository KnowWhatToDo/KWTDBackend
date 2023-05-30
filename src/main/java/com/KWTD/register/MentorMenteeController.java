package com.KWTD.register;

import com.KWTD.register.mentee.mentee;
import com.KWTD.register.mentor.mentor;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.*;

@RestController
public class MentorMenteeController {
    // make controller to find if the entered number is for a mentee or mentor by
    // searching in firebase
    @GetMapping("/search")
    public String isMentorMentee(@RequestParam String phoneNumber) throws InterruptedException, ExecutionException {

        if (isMentor(phoneNumber)) {
            return "mentor";
        } else if (isMentee(phoneNumber)) {
            return "mentee";
        } else {
            return "not found";
        }

    }

    Firestore dbFirestore = FirestoreClient.getFirestore();

    public boolean isMentor(String phoneNumber) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("mentor_user").document(phoneNumber);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        mentor mentor;
        if (document.exists()) {
            mentor = document.toObject(mentor.class);
            return true;
        } else {
            return false;
        }

    }

    public boolean isMentee(String phoneNumber) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("mentee_user").document(phoneNumber);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        mentee mentee;
        if (document.exists()) {
            mentee = document.toObject(mentee.class);
            return true;
        } else {
            return false;
        }
    }

}