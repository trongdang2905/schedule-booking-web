package com.example.schedulebookingweb.scheduler;

import com.example.schedulebookingweb.model.Meeting;
import com.example.schedulebookingweb.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReminderScheduler {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private NotificationFacade notificationFacade;

    @Scheduled(fixedDelay = 60000)   // run every 60s
    public void processReminders() {
        System.out.println("Scheduler running: " + java.time.LocalDateTime.now());
        List<Meeting> due = meetingService.findDueReminders();
        for (Meeting m : due) {
            try {
                notificationFacade.send(m);
                meetingService.markSent(m);       // status = SENT
            } catch (Exception e) {
                meetingService.handleRetry(m, e); // retry logic

            }
        }
    }

}
