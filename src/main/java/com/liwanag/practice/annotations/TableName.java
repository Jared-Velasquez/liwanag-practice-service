package com.liwanag.practice.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Sample: https://www.baeldung.com/spring-data-dynamodb

@Target(TYPE)
@Retention(RUNTIME)
public @interface TableName {
    String name();
}
