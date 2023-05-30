package com.KWTD.register.mentee;

import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.KWTD.register.mentor.mentor;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MenteeServices {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    public String createMENTEE(mentee mentee, String phone) throws InterruptedException, ExecutionException {

        List<String> mentorUIDs = getMentorUIDs();
        System.out.println(mentorUIDs);

        if (!isMentorUIDExists(mentorUIDs, mentee)) {
            return "mentorUID not found";
        } else {
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("mentee_user").document(phone)
                    .set(mentee);
            return collectionsApiFuture.get().getUpdateTime().toString();
        }
    }

    public mentee getMENTEE(String phone) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("mentee_user").document(phone);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        mentee mentee;
        if (document.exists()) {
            mentee = document.toObject(mentee.class);
            return mentee;
        } else {
            return null;
        }

    }

    public String updateMENTEE(mentee mentee, String phone) throws InterruptedException, ExecutionException {
        // Check if mentorUID exists
        List<String> mentorUIDs = getMentorUIDs();
        if (!isMentorUIDExists(mentorUIDs, mentee)) {
            return "mentorUID not found";
        }

        // Perform the update
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("mentee_user").document(phone)
                .set(mentee);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteMENTEE(String phone) {
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("mentee_user").document(phone).delete();
        return "Document with ID " + phone + " has been deleted";
    }

    public boolean isMentorUIDExists(List<String> mentorUIDs, mentee mentee) {
        if (mentee.getMentors() == null) {
            return true; // Allow null mentors
        }

        for (String mentorUID : mentorUIDs) {
            if (mentee.getMentors().contains(mentorUID)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getMentorUIDs() {
        List<String> mentorUIDs = new ArrayList<>();

        // get doucment id of all mentors
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("mentor_user").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                mentorUIDs.add(document.getId());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return mentorUIDs;
    }
}
