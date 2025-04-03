package com.winterhold.notification_service.consumer;

import com.winterhold.base.dto.LoanNotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoanNotificationConsumer {

    private final String topic;
    private final String group;

    public LoanNotificationConsumer(
            @Value("${spring.kafka.topic.loan}") String topic,
            @Value("${spring.kafka.consumer.group}") String group
    ) {
        this.topic = topic;
        this.group = group;
    }

    @KafkaListener(topics = "#{'${spring.kafka.topic.loan}'}", groupId = "#{'${spring.kafka.consumer.group}'}")
    public void consumeNotification(LoanNotificationDto dto) {
        System.out.println("Received Loan Notification: " + dto.getMessage());
    }
}
