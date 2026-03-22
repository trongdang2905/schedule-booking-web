package com.example.schedulebookingweb.notification;

import com.example.schedulebookingweb.model.Channel;
import com.example.schedulebookingweb.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService implements NotificationService{

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }

    @Override
    public void sendNotification(Meeting meeting) {
        SimpleMailMessage message = new SimpleMailMessage();
        String[] to = meeting.getRecipientId().split(",");
        message.setTo(to);
        message.setSubject(buildSubject(meeting));
        message.setText(buildEmailContent(meeting));
        mailSender.send(message);
    }

    //Set up subject
    private String buildSubject(Meeting meeting){
        return "[Meeting Scheduler] " + meeting.getTitle();
    }

    //Set up inner text
    private String buildEmailContent(Meeting meeting){

        return """
            Hello,

            You have a new meeting scheduled.

            Title: %s
            Description: %s
            Meeting Time: %s

            Please check the system for more details.

            Best regards,
            Meeting Scheduler System
            """.formatted(
                meeting.getTitle(),
                meeting.getDescription(),
                formatMeetingTime(meeting.getMeetingTime())
        );
    }

    public String formatMeetingTime(LocalDateTime meetingTime) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return meetingTime.format(formatter);
    }

}
