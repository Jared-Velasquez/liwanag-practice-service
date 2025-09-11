package com.liwanag.practice.ports.secondary;

import com.liwanag.practice.domain.dto.content.UnitOverviewDTO;
import com.liwanag.practice.domain.model.content.FqId;
import com.liwanag.practice.domain.model.questions.Question;

import java.util.List;
import java.util.NoSuchElementException;

public interface QuestionStore {
    /**
     * Load questions by fully qualified activity id
     * @param fqid
     * @return List of Questions to be used in the session
     */
    List<Question> loadQuestions(FqId fqid) throws NoSuchElementException;

    /**
     * Load the full canonical Liwanag content (units, their episodes, and their activities)
     * @return List of UnitOverviewDTO representing the full content
     */
    // TODO: add loadFullContent to port
    //List<UnitOverviewDTO> loadFullContent();
}
