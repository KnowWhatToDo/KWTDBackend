package com.KWTD.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.KWTD.models.Meeting;
import com.KWTD.models.MeetingList;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MeetingService {
    Firestore database = FirestoreClient.getFirestore();

    public String generateMeetingId(Meeting meeting){
        String id="";
        String part1 = meeting.getMentorNumber();
        String part2 = "";
        String [] part2Arr = meeting.getMeetingDate().split("-");
        for(String s: part2Arr){
            part2+=s;
        }
        String part3="";
        String [] part3Arr = meeting.getMeetingTime().split(":");
        for(String s: part3Arr){
            part3+=s;
        }
        id = part1 + part2 + part3;
        return id;
    }

    public String createMeeting(Meeting meeting){
        String status = "";
        DocumentReference ref = database.collection("meetings")
                                .document(meeting.getMentorNumber());
        try {
            DocumentSnapshot snapshot = ref.get().get();
            if(snapshot.exists()){
                MeetingList meetingList = snapshot.toObject(MeetingList.class);
                if(meetingList.getSlots().contains(meeting)){
                    return "record-already-exists";
                }
                meeting.setMeetingId(generateMeetingId(meeting));
                meetingList.getSlots().add(meeting);
                ApiFuture<WriteResult> result = ref.set(meetingList);
                status = "Completed Write: " + result.get().getUpdateTime().toString();
            }else{
                MeetingList meetingList = new MeetingList();
                List<Meeting> meetings = new ArrayList<>();
                meeting.setMeetingId(generateMeetingId(meeting));
                meetings.add(meeting);
                meetingList.setSlots(meetings);
                ApiFuture<WriteResult> result = ref.set(meetingList);
                status = "Completed Write at: " + result.get().getUpdateTime().toString();
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

    public String updateMeeting(Meeting meeting){
        String status = "";
        DocumentReference ref = database.collection("meetings")
                                    .document(meeting.getMentorNumber());
        try {
            DocumentSnapshot snapshot = ref.get().get();
            if(snapshot.exists()){
                MeetingList meetingList = snapshot.toObject(MeetingList.class);
                if(meetingList.getSlots().contains(meeting)){
                    List<Meeting> meetings = meetingList.getSlots();
                    int index = meetingList.getSlots().indexOf(meeting);
                    meeting.setMeetingId(generateMeetingId(meeting));
                    meetings.set(index, meeting);
                    meetingList.setSlots(meetings);
                    ref.set(meetingList);
                    status = meeting.getMeetingId();
                }else{
                    return "document-does-not-exist";
                }
            }
        } catch(InterruptedException e){
            status = e.toString();
            e.printStackTrace();
        } catch (Exception e) {
            status = e.toString();
            e.printStackTrace();
        }
        return status;
    }

}
