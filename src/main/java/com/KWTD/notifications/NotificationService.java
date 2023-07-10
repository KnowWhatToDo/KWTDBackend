package com.KWTD.notifications;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.Message.Builder;



@Service
public class NotificationService {

    /**
     * @param title Title of the notification
     * @param body body of the notification sent
     * @param topic in most cases topic is the number of the user, 
     * to whom notification is to be sent ...
     * or a group of users under a particular topic
     * @return if the notification was successfully sent or not
     * 
     */
    public String sendNotification(String title, String body, String topic){
        String response = "unsuccessful";
        Notification notification = Notification.builder()
                                            .setTitle(title)
                                            .setBody(body)
                                            .build();

        Message message = Message.builder()
                                    .setNotification(notification)
                                    .setTopic(topic)
                                    .build();

        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            response = "message-send-error";
            e.printStackTrace();
        }
        return response;
    }

}
