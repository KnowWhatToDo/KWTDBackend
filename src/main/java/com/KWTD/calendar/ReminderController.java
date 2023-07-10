package com.KWTD.calendar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;

import com.KWTD.meeting.Meeting;
import com.KWTD.meeting.MeetingList;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@RestController
@Component
public class ReminderController {
    private static Firestore database = FirestoreClient.getFirestore();

    
    /**
     * For checking for meetings and sending reminders
     * an hour before one's schedule meeting time
     */
    @Scheduled(cron = "0 0 * * * *")
    public void sendReminder(){
        //TODO: Using Firebase Cloud Messaging
    }

    
    /**
     * Makes a document of a the day's date to collect all upcoming meetings 
     */
    @Scheduled(cron = "0 0 0 * * *")
    void listMeetingsOfTheDay(){
        // Getting all meeting documents
        CollectionReference meetingCollection = database.collection("meetings");
        try {
            QuerySnapshot collectionSnapshot = meetingCollection.get().get();
            
            if(collectionSnapshot!=null){
                List<QueryDocumentSnapshot> documentsSnapshot = collectionSnapshot.getDocuments();
                List<MeetingList> meetingDocuments = new ArrayList<>();                

                for(int i=0;i<documentsSnapshot.size();i++){
                    meetingDocuments.add(documentsSnapshot.get(i).toObject(MeetingList.class));                    
                }

                List<Meeting> meetingsToday = new ArrayList<>();
                for(int i=0;i<meetingDocuments.size();i++){
                   for(int j=0;j<meetingDocuments.get(i).getSlots().size();j++){
                        if(meetingDocuments.get(i).getSlots()
                                .get(j).getMeetingDate().equals(LocalDate.now().toString())){
                                meetingsToday.add(meetingDocuments.get(i).getSlots().get(j));
                        }
                   }
                }
                DocumentReference ref = database.collection("meetings_today")
                                                .document(LocalDate.now().toString());
                MeetingList scheduleOfTheDay = new MeetingList();
                scheduleOfTheDay.setSlots(meetingsToday);
                ref.set(scheduleOfTheDay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
