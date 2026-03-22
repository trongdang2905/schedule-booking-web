package com.example.schedulebookingweb.notification;

import com.example.schedulebookingweb.model.Channel;
import com.example.schedulebookingweb.model.Meeting;

public interface NotificationService {
    Channel getChannel();
    void sendNotification(Meeting meeting);
}
