package com.liwanag.practice.models.session;

import com.liwanag.practice.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

@DynamoDbBean
@TableName(name = "SessionTable")
@Getter
@Setter
public class Session {
    private String pk;
    private String sk;
    private String entityType;
    private String activityFqId;
    private Integer activityVersion;
    private String status; // IDLE || IN_PROGRESS || FINISHED
    private String orderS3Key;

    private String currentQuestion;
    private String turnToken;
    private Long leaseExpiresAt;

    private Integer attempted;
    private Integer correct;

    private Long createdAt;
    private Long updatedAt;
    private Long firstStartedAt;
    private Long lastAnsweredAt;
    private Long completedAt;
    private Long ttl;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return sk;
    }
}
