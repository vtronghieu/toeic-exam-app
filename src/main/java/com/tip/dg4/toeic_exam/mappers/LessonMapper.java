package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ContentDto;
import com.tip.dg4.toeic_exam.dto.LessonDto;
import com.tip.dg4.toeic_exam.dto.requests.LessonReq;
import com.tip.dg4.toeic_exam.models.Content;
import com.tip.dg4.toeic_exam.models.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LessonMapper {
    private final ContentMapper contentMapper;

    public Lesson convertReqToModel(LessonReq lessonREQ) {
        return Lesson.builder()
                .id(lessonREQ.getId())
                .partId(lessonREQ.getPartId())
                .name(lessonREQ.getName())
                .build();
    }

    public Lesson convertDtoToModel(LessonDto lessonDto) {
        List<Content> contents = contentMapper.convertDTOsToModels(lessonDto.getContents());

        return Lesson.builder()
                .id(lessonDto.getId())
                .partId(lessonDto.getPartId())
                .name(lessonDto.getName())
                .contents(contents)
                .build();
    }

    public LessonDto convertModelDto(Lesson lesson) {
        List<ContentDto> contentDTOs = contentMapper.convertModelsToDTOs(lesson.getContents());

        return LessonDto.builder()
                .id(lesson.getId())
                .partId(lesson.getPartId())
                .name(lesson.getName())
                .contents(contentDTOs)
                .build();
    }

    public List<LessonDto> convertModelsToDTOs(List<Lesson> lessons) {
        return Optional.ofNullable(lessons).orElse(Collections.emptyList()).stream()
                .map(this::convertModelDto).toList();
    }

    public List<Lesson> convertDTOsToModels(List<LessonDto> lessonDTOs) {
        return Optional.ofNullable(lessonDTOs).orElse(Collections.emptyList()).stream()
                .map(this::convertDtoToModel).toList();
    }

}
