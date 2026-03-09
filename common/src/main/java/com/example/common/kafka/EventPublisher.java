package com.example.common.kafka;

import com.example.common.kafka.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public void publish(String topic, Event event){
        kafkaTemplate.send(topic, event);
    }
}
