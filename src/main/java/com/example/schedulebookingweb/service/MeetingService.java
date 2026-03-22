package com.example.schedulebookingweb.service;


import com.example.schedulebookingweb.model.Meeting;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MeetingService {
    @Transactional
    Meeting create(Meeting meeting);

    List<Meeting> findDueReminders();

    void cancel(Long id);

    void updateMeeting(Meeting meeting);

    void markSent(Meeting meeting);

    List<Meeting> findAllMeetings();

    void handleRetry(Meeting meeting, Exception exception);

    Meeting update(Long id, Meeting meeting);
}
