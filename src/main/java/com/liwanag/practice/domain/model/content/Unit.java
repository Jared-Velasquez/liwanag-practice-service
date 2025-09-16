package com.liwanag.practice.domain.model.content;

import com.liwanag.practice.annotations.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

// Sample: https://aws.amazon.com/blogs/database/use-spring-cloud-to-capture-amazon-dynamodb-changes-through-amazon-kinesis-data-streams/
@Getter
@Setter
@Builder
public final class Unit {
    private FqId fqid;
    private String title;
    private String description;
}
