package com.liwanag.practice.adapters.secondary.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@RequiredArgsConstructor
@Getter
@Setter
public class ActivityEntity {
    private String pk;
    private String sk;
    private String entityType;
    private String unitId;
    private String episodeId;
    private String activityId;
    private String activityFqId;
    private String title;
    private String locale;
    private Integer version;
    private String manifestS3Key;
    private Integer totalQuestions;
    private Long updatedAt;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return sk;
    }
}