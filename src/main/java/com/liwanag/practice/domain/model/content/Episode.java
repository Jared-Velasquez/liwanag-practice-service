package com.liwanag.practice.domain.model.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Episode {
    private FqId fqid;
    private String title;
}
