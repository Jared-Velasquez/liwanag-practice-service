package com.liwanag.practice.models.content;

import com.liwanag.practice.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

// Sample: https://aws.amazon.com/blogs/database/use-spring-cloud-to-capture-amazon-dynamodb-changes-through-amazon-kinesis-data-streams/

@DynamoDbBean // tells Spring Cloud that this entity needs to be mapped to a DynamoDB table; table name must be overridden via DI
@TableName(name = "ContentTable")
@Getter
@Setter
public class Unit {
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
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    public String getSk() {
        return sk;
    }
}
