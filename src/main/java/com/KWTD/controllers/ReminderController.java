package com.KWTD.calendar;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.LocalDate;

import com.KWTD.meeting.Meeting;
import com.KWTD.meeting.MeetingList;
import com.KWTD.notifications.NotificationService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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
     * an hour before one's scheduled meeting time
     */
    // @Scheduled(cron = "0 0 * * * *")
    @Scheduled(fixedRate = 5000)
    public void sendReminder(){
        System.out.println("listend meetings");
        DocumentReference dailyMeetings = database.collection("meetings+today")
                                                .document(LocalDate.now().toString());
        
        try {
            DocumentSnapshot snapshot = dailyMeetings.get().get();
            MeetingList dailyMeetingList = new MeetingList();
            if(snapshot.exists()){
                dailyMeetingList = snapshot.toObject(MeetingList.class);
                List<Meeting> meetings = dailyMeetingList.getSlots();
                for(int i=0;i<meetings.size();i++){
                    NotificationService service = new NotificationService();
    
                    LocalTime meetingTime = LocalTime.parse(meetings.get(i)
                                                    .getMeetingTime());
                    LocalTime timeNow = LocalTime.now();

                    long timeDifference = timeNow.until(meetingTime, ChronoUnit.MINUTES);

                    if(timeDifference<90 && timeDifference>0){
                        // sends notification to mentee
                        service.sendNotification("Meeting Reminder", 
                            "You have an upcoming meeting at " + meetings.get(i).getMeetingTime() + ".",
                            meetings.get(i).getMenteeNumber());

                        // sends notification to mentor
                         service.sendNotification("Meeting Reminder", 
                            "You have an upcoming meeting at " + meetings.get(i).getMeetingTime() + ".",
                            meetings.get(i).getMentorNumber());
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

                // filling meeting list with meetings happening on a prticular day...
                for(int i=0;i<meetingDocuments.size();i++){
                   for(int j=0;j<meetingDocuments.get(i).getSlots().size();j++){
                        if(meetingDocuments.get(i).getSlots()
                                .get(j).getMeetingDate().equals(LocalDate.now().toString())){
                                meetingsToday.add(meetingDocuments.get(i).getSlots().get(j));
                        }
                   }
                }

                // writing meetings of the day on the database
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
