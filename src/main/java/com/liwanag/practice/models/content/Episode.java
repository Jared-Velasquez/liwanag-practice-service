package com.liwanag.practice.models.content;

import com.liwanag.practice.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

@DynamoDbBean
@TableName(name = "ContentTable")
@Getter
@Setter
public class Episode {
    private String pk;
    private String sk;
    private String entityType;
    private String unitId;
    private String episodeId;
    private String title;
    private List<String> activityIds;
    private List<String> activityFqIds;
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
