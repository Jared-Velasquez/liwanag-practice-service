package com.liwanag.practice.domain.model.session;

import com.liwanag.practice.annotations.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;
import java.util.UUID;

@DynamoDbBean
@TableName(name = "SessionTable")
@Getter
@Setter
@Builder
public class Session {
    private String pk;
    private String sk;
    private String activityFqId;
    private Integer activityVersion;
    private Status status;
    private String orderS3Key;

    private String currentQuestion;
    private UUID turnToken;
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

    public enum Status {
        IDLE,
        IN_PROGRESS,
        FINISHED
    }
}
