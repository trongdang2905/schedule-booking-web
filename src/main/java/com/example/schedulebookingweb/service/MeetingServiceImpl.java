package com.example.schedulebookingweb.service;

import com.example.schedulebookingweb.model.Meeting;
import com.example.schedulebookingweb.model.MeetingStatus;
import com.example.schedulebookingweb.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    @Transactional
    public Meeting create(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public List<Meeting> findDueReminders() {
         return meetingRepository.findDueAndLock(LocalDateTime.now());
    }

    @Override
    public void cancel(Long id) {
         meetingRepository.deleteById(id);
    }

    @Override
    public void updateMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public void markSent(Meeting meeting) {
        meeting.setStatus(MeetingStatus.SENT);
        meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public void handleRetry(Meeting meeting, Exception exception) {

    }

    @Override
    @Transactional
    public Meeting update(Long id, Meeting meeting) {
        Meeting meeting1 = meetingRepository.findById(id).orElseThrow(() -> new RuntimeException("Meeting not found"));

        meeting1.setStatus(meeting.getStatus());
        meeting1.setTitle(meeting.getTitle());
        meeting1.setDescription(meeting.getDescription());
        meeting1.setMeetingTime(meeting.getMeetingTime());
        meeting1.setRecipientId(meeting.getRecipientId());
        meeting1.setChannel(meeting.getChannel());
        meeting1.setUpdatedAt(LocalDateTime.now());
        meeting1.setCreatedAt(meeting.getCreatedAt());
        meeting1.setRemindAt(meeting.getRemindAt());
        meeting1.setReminderSent(meeting.getReminderSent());
        meeting1.setCreatedBy(meeting.getCreatedBy());
        meeting1.setRemindOffset(meeting.getRemindOffset());
        meeting1.setRetryCount(meeting.getRetryCount());
        meeting1.setRecipientType(meeting.getRecipientType());
        meetingRepository.save(meeting1);
        return meeting1;
    }
}
