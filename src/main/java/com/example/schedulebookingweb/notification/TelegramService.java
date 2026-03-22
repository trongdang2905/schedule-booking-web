package com.example.schedulebookingweb.notification;

import com.example.schedulebookingweb.model.Channel;
import com.example.schedulebookingweb.model.Meeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class TelegramService implements NotificationService{

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public Channel getChannel() {
        return Channel.TELEGRAM;
    }

    @Override
    public void sendNotification(Meeting meeting) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Map<String, String> body = Map.of(
                "chat_id",    meeting.getRecipientId(),
                "parse_mode", "HTML",
                "text",       String.format("<b>📅 Nhắc lịch họp</b>\n" +
                                "<b>Tiêu đề:</b> %s\n" +
                                "<b>Thời gian:</b> %s\n" +
                                "<b>Nội dung:</b> %s",
                        meeting.getTitle(),
                        meeting.getMeetingTime().format(formatter),
                        meeting.getDescription()
                )
        );
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, body, String.class);
    }


}
