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
        /*Meeting meeting2 = new Meeting();
        meeting2.setMeetingDate("2023-07-08");
        meeting2.setMeetingLink("https://some_random_url.com/meeting_id");
        meeting2.setMeetingTime("15:00");
        meeting2.setMenteeNumber("N/A");
        meeting2.setMentorNumber("5544332211");
        status = meeting.equals(meeting2) == true? "true":"false";*/
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
