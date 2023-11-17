package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.ContentDto;
import com.tip.dg4.toeic_exam.models.Content;

import java.util.List;
import java.util.UUID;

public interface ContentService {
    void createContent(ContentDto contentDTO);

    List<ContentDto> getContents();

    ContentDto getContentById(UUID id);

    List<ContentDto> getContentsByLessonId(UUID lessonId);

    void updateContent(ContentDto contentDTO);

    void deleteContentById(UUID id);

    List<Content> findAll();
}
