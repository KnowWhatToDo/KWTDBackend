package com.KWTD.mentoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MentoringService {

    private final Firestore dbFirestore = FirestoreClient.getFirestore();

    public String createMENTORING(Mentoring mentoring) throws InterruptedException, ExecutionException {
        List<String> mentorUIDs = getMentorUIDs();
        List<String> menteeUIDs = getMeteeUIDs();

        if (isMentorUIDAlreadyExists(mentorUIDs, mentoring) && isMenteeUIDAlreadyExists(menteeUIDs, mentoring)
                && isMenteeUIDExists(menteeUIDs, mentoring)) {
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("mentoring")
                    .document(mentoring.getMentor())
                    .set(mentoring);
            return collectionsApiFuture.get().getUpdateTime().toString();
        } else {
            return "mentorUID or menteeUID not found";
        }
    }

    public Mentoring getMENTORINGByMentor(String mentor) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFirestore.collection("mentoring").document(mentor);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(Mentoring.class);
        } else {
            return null;
        }
    }

    public String updateMENTORING(Mentoring mentoring) throws InterruptedException, ExecutionException {
        String mentor = mentoring.getMentor();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("mentoring").document(mentor)
                .set(mentoring);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteMENTORING(String mentor) {
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("mentoring").document(mentor).delete();
        return "Document with ID " + mentor + " has been deleted";
    }

    public List<String> getMentorUIDs() {
        List<String> mentorUIDs = new ArrayList<>();

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

    public List<String> getMeteeUIDs() {
        List<String> menteeUIDs = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("mentee_user").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                menteeUIDs.add(document.getId());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return menteeUIDs;
    }

    public List<String> getMenteeNumber() {
        List<String> menteeNumber = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("mentee_user").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                menteeNumber.add(document.getString("phoneNumber"));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return menteeNumber;
    }

    public boolean isMenteeUIDExists(List<String> menteeUIDs, Mentoring mentoring) {
        List<String> menteeUIDList = new ArrayList<>(menteeUIDs);
        for (String uid : menteeUIDList) {
            if (mentoring.getMenteeUIDs().contains(uid)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMentorUIDAlreadyExists(List<String> mentorUIDs, Mentoring mentoring) {
        return mentorUIDs.contains(mentoring.getMentor());
    }

    public boolean isMenteeUIDAlreadyExists(List<String> menteeUIDs, Mentoring mentoring) {
        List<String> menteeUIDList = new ArrayList<>(menteeUIDs);
        for (String uid : menteeUIDList) {
            if (mentoring.getMenteeUIDs().contains(uid)) {
                return true;
            }
        }
        return false;
    }
}
