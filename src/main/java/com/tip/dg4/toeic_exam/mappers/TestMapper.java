package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.TestDto;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.models.Test;
import com.tip.dg4.toeic_exam.models.enums.PracticeType;
import com.tip.dg4.toeic_exam.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestMapper {
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;

    public Test convertDtoToModel(TestDto testDto) {
        List<UUID> questionIDs = questionService.getQuestionIDsByQuestions(testDto.getQuestions());

        return Test.builder()
                .id(testDto.getId())
                .partId(testDto.getPartId())
                .type(PracticeType.getType(testDto.getType()))
                .name(testDto.getName())
                .questionIDs(questionIDs)
                .build();
    }

    public TestDto convertModelToDto(Test test) {
        List<QuestionDto> questionDTOs = Optional.of(questionService.findByIDs(test.getQuestionIDs()))
                .orElse(Collections.emptyList()).stream()
                .map(questionMapper::convertModelToDto)
                .toList();

        return TestDto.builder()
                .id(test.getId())
                .partId(test.getPartId())
                .type(PracticeType.getValueType(test.getType()))
                .questions(questionDTOs)
                .build();
    }

    public List<Test> convertDTOsToModels(List<TestDto> testDTOs) {
        return Optional.ofNullable(testDTOs).orElse(Collections.emptyList()).stream()
                .map(this::convertDtoToModel).toList();
    }

    public List<TestDto> convertModelsToDTOs(List<Test> tests) {
        return Optional.ofNullable(tests).orElse(Collections.emptyList()).stream()
                .map(this::convertModelToDto).toList();
    }
}
