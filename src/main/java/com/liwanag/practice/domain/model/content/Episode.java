package com.liwanag.practice.domain.model.content;

import com.liwanag.practice.annotations.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

@Getter
@Setter
@Builder
public class Episode {
    private FqId fqid;
    private String title;
}
