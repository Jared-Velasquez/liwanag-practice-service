package com.liwanag.practice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyObjectPair<T> {
    private String key;
    private T obj;
}
