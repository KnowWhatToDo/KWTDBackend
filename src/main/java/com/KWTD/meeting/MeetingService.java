package com.KWTD.meeting;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MeetingService {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    public String createMeeting(Meeting meeting, String mentorNumber) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("meeting").document(mentorNumber)
                .set(meeting);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Meeting getMeeting(String mentorNumber) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("meeting").document(mentorNumber);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Meeting meeting;
        if (document.exists()) {
            meeting = document.toObject(Meeting.class);
            return meeting;
        } else {
            return null;
        }

    }

    public String updateMeeting(Meeting meeting, String mentorNumber) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("meeting").document(mentorNumber)
                .set(meeting);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteMeeting(String mentorNumber) {
        ApiFuture<WriteResult> future = dbFirestore.collection("meeting").document(mentorNumber).delete();
        return "Document with Mentor Number " + mentorNumber + " has been deleted";
    }

}
