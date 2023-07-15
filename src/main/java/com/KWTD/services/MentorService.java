package com.KWTD.services;

import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.KWTD.models.Mentor;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MentorService {

    Firestore dbFirestore = FirestoreClient.getFirestore();

    public String createMentor(Mentor mentor, String phone) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("mentor_user")
                .document(phone)
                .set(mentor);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Mentor getMentor(String phone) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("mentor_user").document(phone);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Mentor mentor;
        if (document.exists()) {
            mentor = document.toObject(Mentor.class);
            return mentor;
        } else {
            return null;
        }

    }

    public String updateMentor(Mentor mentor, String phone) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("mentor_user").document(phone)
                .set(mentor);
        return collectionApiFuture.get().getUpdateTime().toString();

    }

    public String deleteMentor(String phone) {
        dbFirestore.collection("mentor_user").document(phone).delete();
        return "Document with ID " + phone + " has been deleted";
    }
}
