package com.liwanag.practice.config;

import com.liwanag.practice.adapters.secondary.persistence.entity.ActivityEntity;
import com.liwanag.practice.adapters.secondary.persistence.entity.EpisodeEntity;
import com.liwanag.practice.adapters.secondary.persistence.entity.SessionEntity;
import com.liwanag.practice.adapters.secondary.persistence.entity.UnitEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class TableConfig {
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.create();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    @Qualifier("unitTable")
    public DynamoDbTable<UnitEntity> unitTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table("ContentTable", TableSchema.fromBean(UnitEntity.class));
    }

    @Bean
    @Qualifier("episodeTable")
    public DynamoDbTable<EpisodeEntity> episodeTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table("ContentTable", TableSchema.fromBean(EpisodeEntity.class));
    }

    @Bean
    @Qualifier("activityTable")
    public DynamoDbTable<ActivityEntity> activityTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table("ContentTable", TableSchema.fromBean(ActivityEntity.class));
    }

    @Bean
    @Qualifier("sessionTable")
    public DynamoDbTable<SessionEntity> sessionTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table("SessionTable", TableSchema.fromBean(SessionEntity.class));
    }
}
