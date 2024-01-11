package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.ContentDto;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.ContentMapper;
import com.tip.dg4.toeic_exam.models.Content;
import com.tip.dg4.toeic_exam.models.Lesson;
import com.tip.dg4.toeic_exam.services.ContentService;
import com.tip.dg4.toeic_exam.services.LessonService;
import com.tip.dg4.toeic_exam.services.PracticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final PracticeService practiceService;
    private final LessonService lessonService;
    private final ContentMapper contentMapper;

    /**
     * Creates new content.
     *
     * @param contentDTO the content to create
     * @throws NotFoundException if the lesson associated with the content does not exist
     * @throws ConflictException if content with the same title already exists in the lesson
     * @throws TExamException    if an unexpected error occurs
     */
    @Override
    public void createContent(ContentDto contentDTO) {
        try {
            Lesson lesson = lessonService.findById(contentDTO.getLessonId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.LESSON_E002));
            if (existsInLessonByTitle(lesson, contentDTO.getTitle())) {
                throw new ConflictException(ExceptionConstant.CONTENT_E004);
            }

            List<Content> contents = Optional.ofNullable(lesson.getContents()).orElse(new ArrayList<>());

            contents.add(contentMapper.convertDtoToModel(contentDTO));
            lesson.setContents(contents);
            practiceService.saveByLesson(lesson);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets all content.
     *
     * @return a list of all content DTOs
     * @throws TExamException if an unexpected error occurs
     */
    @Override
    public List<ContentDto> getContents() {
        try {
            return contentMapper.convertModelsToDTOs(this.findAll());
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets content by identifier.
     *
     * @param id the identifier of the content to get
     * @return a content DTO, or empty if the content does not exist
     * @throws TExamException if an unexpected error occurs
     */
    @Override
    public ContentDto getContentById(UUID id) {
        try {
            return this.findById(id)
                    .map(contentMapper::convertModelToDTO)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.CONTENT_E005));
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Gets content by lesson identifier.
     *
     * @param lessonId the identifier of the lesson to get content for
     * @return a list of content DTOs, or empty if the lesson does not exist or has no content
     * @throws TExamException if an unexpected error occurs
     */
    @Override
    public List<ContentDto> getContentsByLessonId(UUID lessonId) {
        try {
            Lesson lesson = lessonService.findById(lessonId)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.LESSON_E002));

            return contentMapper.convertModelsToDTOs(lesson.getContents());
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Updates existing content.
     *
     * @param contentDTO the content to update
     * @throws NotFoundException if the lesson associated with the content does not exist, or if the content to update does not exist
     * @throws ConflictException if content with the same title already exists in the lesson, but is not the content being updated
     * @throws TExamException    if an unexpected error occurs
     */
    @Override
    public void updateContent(ContentDto contentDTO) {
        try {
            Lesson lesson = lessonService.findById(contentDTO.getLessonId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.LESSON_E002));

            lesson.getContents().stream()
                    .filter(content -> contentDTO.getId().equals(content.getId()))
                    .findFirst()
                    .map(content -> {
                        if (!Objects.equals(contentDTO.getContent(), content.getContent()) &&
                                existsInLessonByTitle(lesson, contentDTO.getTitle())) {
                            throw new ConflictException(ExceptionConstant.CONTENT_E004);
                        }
                        content.setLessonId(contentDTO.getLessonId());
                        content.setTitle(contentDTO.getTitle());
                        content.setContent(contentDTO.getContent());
                        content.setExamples(contentDTO.getExamples());

                        return content;
                    }).orElseThrow(() -> new NotFoundException(ExceptionConstant.CONTENT_E005));
            practiceService.saveByLesson(lesson);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Deletes content by identifier.
     *
     * @param id the identifier of the content to delete
     * @throws NotFoundException if the content does not exist
     * @throws TExamException    if an unexpected error occurs
     */
    @Override
    public void deleteContentById(UUID id) {
        try {
            Content content = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.CONTENT_E005));
            Lesson lesson = lessonService.findById(content.getLessonId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.LESSON_E002));

            lesson.getContents().remove(content);
            practiceService.saveByLesson(lesson);
        } catch (Exception e) {
            throw new TExamException(e);
        }
    }

    /**
     * Finds all content.
     *
     * @return a list of all content
     */
    @Override
    public List<Content> findAll() {
        return lessonService.findAll().stream()
                .flatMap(lesson -> Optional.ofNullable(lesson.getContents()).orElse(Collections.emptyList()).stream())
                .toList();
    }

    /**
     * Checks if content exists in a lesson by title.
     *
     * @param lesson the lesson to check
     * @param title  the title of the content to check for
     * @return true if the content exists in the lesson, false otherwise
     */
    private boolean existsInLessonByTitle(Lesson lesson, String title) {
        return Objects.nonNull(lesson.getContents()) &&
                lesson.getContents().stream().anyMatch(content -> title.equals(content.getTitle()));
    }

    /**
     * Finds content by identifier.
     *
     * @param id the identifier of the content to find
     * @return an optional containing the content, or empty if the content does not exist
     */
    private Optional<Content> findById(UUID id) {
        return this.findAll().stream()
                .filter(content -> id.equals(content.getId()))
                .findFirst();
    }
}
