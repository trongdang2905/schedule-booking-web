package com.example.schedulebookingweb.scheduler;

import com.example.schedulebookingweb.model.Channel;
import com.example.schedulebookingweb.model.Meeting;
import com.example.schedulebookingweb.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationFacade {


    private final Map<Channel, NotificationService>  notificationServiceMap;

    public NotificationFacade(List<NotificationService> services) {

        this.notificationServiceMap = services.stream()
                .collect(Collectors.toMap(
                        NotificationService::getChannel,
                        Function.identity()
                ));
    }

    public void send(Meeting meeting){
        Channel channel = meeting.getChannel();
        if(channel == Channel.BOTH){
            notificationServiceMap.get(Channel.TELEGRAM).sendNotification(meeting);
            notificationServiceMap.get(Channel.EMAIL).sendNotification(meeting);
            return;
        }
        NotificationService notificationService = notificationServiceMap.get(meeting.getChannel());
        notificationService.sendNotification(meeting);
    }
}
