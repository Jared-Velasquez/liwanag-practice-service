package com.liwanag.practice.adapters.secondary.persistence.entity;

import com.liwanag.practice.annotations.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@TableName(name = "SessionTable")
@Getter
@Setter
@Builder
public class SessionEntity {
    private String pk;
    private String sk;
    private String activityFqId;
    private Integer activityVersion;
    private String status;

    private Integer currentIndex;
    private String turnToken;
    private Long leaseExpiresAt;
    private String manifestS3Key;

    private Integer attempted;
    private Integer correct;

    private Long createdAt;
    private Long updatedAt;
    private Long completedAt;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return sk;
    }
}
