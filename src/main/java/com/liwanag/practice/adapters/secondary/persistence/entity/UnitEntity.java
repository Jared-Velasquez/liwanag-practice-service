package com.liwanag.practice.adapters.secondary.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

@DynamoDbBean
// tells Spring Cloud that this entity needs to be mapped to a DynamoDB table; table name must be overridden via DI
@RequiredArgsConstructor
@Getter
@Setter
public class UnitEntity {
    private String pk;
    private String sk;
    private String entityType; // always UNIT_LIVE
    private String unitId;
    private String title;
    private String description;
    private List<String> episodeIds;
    private List<String> episodeFqIds;
    private Long updatedAt;

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