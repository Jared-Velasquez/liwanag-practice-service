package com.liwanag.practice.adapters.secondary.persistence.entity;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    private String pk;
    private String sk;
    private String activityFqId;
    private Integer activityVersion;
    private String status;

    private Integer currentIndex;
    private Integer totalQuestions;
    private String turnToken;
    private String currentAttemptId;
    private Long leaseExpiresAt;
    private String manifestS3Key;

    private Integer attempted;
    private Integer correct;

    private Long createdAt;
    private Long updatedAt;
    private Long completedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSk() {
        return sk;
    }
}
