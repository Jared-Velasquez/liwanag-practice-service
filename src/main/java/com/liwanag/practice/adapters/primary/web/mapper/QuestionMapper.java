package com.liwanag.practice.adapters.primary.web.mapper;

import com.liwanag.practice.adapters.primary.web.dto.questions.*;
import com.liwanag.practice.domain.model.questions.*;
import org.springframework.stereotype.Component;

@Component
public final class QuestionMapper {
    public QuestionDTO toDTO(Question model) {
        return switch (model) {
            case MultipleChoiceQuestion mcq -> MultipleChoiceQuestionDTO.builder()
                    .qid(mcq.qid())
                    .type(mcq.type())
                    .stem(mcq.stem())
                    .difficulty(mcq.difficulty())
                    .tags(mcq.tags())
                    .choices(mcq.choices().stream().map(
                            choice -> ChoiceDTO.builder()
                                    .id(choice.id())
                                    .t(choice.t())
                                    .build()
                            ).toList()
                    )
                    .build();
            case MultipleChoiceMultiQuestion mcmq -> MultipleChoiceQuestionDTO.builder()
                    .qid(mcmq.qid())
                    .type(mcmq.type())
                    .stem(mcmq.stem())
                    .difficulty(mcmq.difficulty())
                    .tags(mcmq.tags())
                    .choices(mcmq.choices().stream().map(
                            choice -> ChoiceDTO.builder()
                                    .id(choice.id())
                                    .t(choice.t())
                                    .build()
                            ).toList()
                    )
                    .build();
            case FillInBlankQuestion fibq -> FillInBlankQuestionDTO.builder()
                    .qid(fibq.qid())
                    .type(fibq.type())
                    .stem(fibq.stem())
                    .difficulty(fibq.difficulty())
                    .tags(fibq.tags())
                    .build();
            case ClozeQuestion clozeq -> ClozeQuestionDTO.builder()
                    .qid(clozeq.qid())
                    .type(clozeq.type())
                    .text(clozeq.text())
                    .difficulty(clozeq.difficulty())
                    .tags(clozeq.tags())
                    .build();
        };
    }
}
