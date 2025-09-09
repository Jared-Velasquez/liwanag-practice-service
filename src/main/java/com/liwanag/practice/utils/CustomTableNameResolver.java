package com.liwanag.practice.utils;

import com.liwanag.practice.annotations.TableName;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import org.springframework.stereotype.Component;

@Component
public class CustomTableNameResolver implements DynamoDbTableNameResolver {
    @Override
    public <T> String resolve(Class<T> clazz) {
        return clazz.getAnnotation(TableName.class).name();
    }
}
