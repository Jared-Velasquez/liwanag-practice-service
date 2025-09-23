package com.liwanag.practice.domain.model.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public final class Activity {
    private FqId fqid;
    private String title;
    private ActivityManifestHandle manifestHandle;
    private Integer totalQuestions;
}
