package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.model.content.Activity;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface CanonicalStore {
    /**
     * Load an activity by its fully qualified ID
     * @param fqid Fully qualified activity ID
     * @return Activity object
     */
    Optional<Activity> loadActivity(FqId fqid);

    /**
     * Load the full canonical Liwanag content (units, their episodes, and their activities)
     * @return List of UnitOverviewDTO representing the full content
     */
    // TODO: add loadFullContent to port
    //List<UnitOverviewDTO> loadFullContent();
}
