package com.KWTD.meeting;

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
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MeetingService {
    Firestore database = FirestoreClient.getFirestore();

    public String createMeeting(Meeting meeting){
        String status = "";
        DocumentReference ref = database.collection("meetings")
                                .document(meeting.getMentorNumber());
        try {
            DocumentSnapshot snapshot = ref.get().get();
            if(snapshot.exists()){
                System.out.println("This sections");
                MeetingList meetingList = snapshot.toObject(MeetingList.class);
                if(meetingList.getSlots().contains(meeting)){
                    return "record-already-exists";
                }
                meetingList.getSlots().add(meeting);
                ApiFuture<WriteResult> result = ref.set(meetingList);
                status = "Completed Write: " + result.get().getUpdateTime().toString();
            }else{
                MeetingList meetingList = new MeetingList();
                List<Meeting> meetings = new ArrayList<>();
                meetings.add(meeting);
                meetingList.setSlots(meetings);
                ApiFuture<WriteResult> result = ref.set(meetingList);
                status = "Completed Write: " + result.get().getUpdateTime().toString();
            }
        } catch (InterruptedException e) {
            status = e.toString();
            e.printStackTrace();
        } catch (ExecutionException e) {
            status = e.toString();
            e.printStackTrace();
        }
        return status;
    }
}
