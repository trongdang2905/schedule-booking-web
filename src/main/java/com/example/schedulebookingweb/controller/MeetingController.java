package com.example.schedulebookingweb.controller;

import com.example.schedulebookingweb.model.Meeting;
import com.example.schedulebookingweb.model.User;
import com.example.schedulebookingweb.service.MeetingService;
import com.example.schedulebookingweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public Meeting createMeeting(@RequestBody Meeting meeting, Authentication authentication) {
        // Lấy thông tin user đang đăng nhập từ Security context
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        // Gán người tạo meeting từ server-side (bảo mật hơn)
        meeting.setCreatedBy(user.getFullName());
        return meetingService.create(meeting);
    }

    @GetMapping()
    public List<Meeting> getMeetingList() {
        return meetingService.findAllMeetings();
    }

    @PutMapping("/{id}")
    public Meeting updateMeeting(@PathVariable Long id, @RequestBody Meeting meeting, Authentication authentication) {
        // Khi update, giữ nguyên createdBy từ server nếu không có
        if (meeting.getCreatedBy() == null || meeting.getCreatedBy().isBlank()) {
            String email = authentication.getName();
            User user = userService.findByEmail(email);
            meeting.setCreatedBy(user.getFullName());
        }
        return meetingService.update(id, meeting);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMeeting(@PathVariable("id") Long id) {

        meetingService.cancel(id);

    }
}
