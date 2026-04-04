package com.example.common.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaProperties {
    private Topics topics = new Topics();

    @Data
    public static class Topics {
        private Topic orderCreated = new Topic();
    }

    @Data
    public static class Topic {
        private String name;
        private String groupId;
        private Integer concurrency;
    }
}