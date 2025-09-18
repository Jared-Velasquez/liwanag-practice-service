package com.liwanag.practice.domain.model.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Sample: https://aws.amazon.com/blogs/database/use-spring-cloud-to-capture-amazon-dynamodb-changes-through-amazon-kinesis-data-streams/
@Getter
@Setter
@Builder
public final class Unit {
    private FqId fqid;
    private String title;
    private String description;
}
