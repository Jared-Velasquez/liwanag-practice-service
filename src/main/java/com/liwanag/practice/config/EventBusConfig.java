package com.liwanag.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

@Configuration
public class EventBusConfig {
    @Bean
    public EventBridgeClient eventBridgeClient() {
        Region region = Region.US_WEST_1;
        return EventBridgeClient.builder()
                .region(region)
                .build();
    }
}
