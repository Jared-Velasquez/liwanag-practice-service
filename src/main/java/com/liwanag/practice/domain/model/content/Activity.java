package com.liwanag.practice.domain.model.content;

import com.liwanag.practice.annotations.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;


@Getter
@Setter
@Builder
public final class Activity {
    private FqId fqid;
    private String title;
    private ActivityManifestHandle manifestHandle;
    private Integer totalQuestions;
}
