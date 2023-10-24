package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.ContentDto;
import com.tip.dg4.toeic_exam.models.Content;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ContentMapper {
    public ContentDto convertModelToDTO(Content content) {
        return ContentDto.builder()
                .id(content.getId())
                .lessonId(content.getLessonId())
                .title(content.getTitle())
                .content(content.getContent())
                .examples(content.getExamples())
                .build();
    }

    public Content convertDtoToModel(ContentDto contentDTO) {
        return Content.builder()
                .id(contentDTO.getId())
                .lessonId(contentDTO.getLessonId())
                .title(contentDTO.getTitle())
                .content(contentDTO.getContent())
                .examples(contentDTO.getExamples())
                .build();
    }

    public List<ContentDto> convertModelsToDTOs(List<Content> contents) {
        return Optional.ofNullable(contents).orElse(Collections.emptyList()).stream()
                .map(this::convertModelToDTO).toList();
    }

    public List<Content> convertDTOsToModels(List<ContentDto> contentDTOs) {
        return Optional.ofNullable(contentDTOs).orElse(Collections.emptyList()).stream()
                .map(this::convertDtoToModel).toList();
    }
}
