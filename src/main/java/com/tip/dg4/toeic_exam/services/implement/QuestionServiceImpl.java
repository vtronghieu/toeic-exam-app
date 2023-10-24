package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.dto.requests.QuestionReq;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.QuestionDetailMapper;
import com.tip.dg4.toeic_exam.mappers.QuestionMapper;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionDetail;
import com.tip.dg4.toeic_exam.models.enums.QuestionLevel;
import com.tip.dg4.toeic_exam.models.enums.QuestionType;
import com.tip.dg4.toeic_exam.repositories.QuestionRepository;
import com.tip.dg4.toeic_exam.services.QuestionDetailService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.UploadService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionDetailMapper questionDetailMapper;
    @Lazy
    private final QuestionDetailService questionDetailService;
    private final UploadService uploadService;

    /**
     * Creates a new question.
     *
     * @param questionReq The question request to create.
     * @throws BadRequestException If the question type or level is invalid.
     * @throws TExamException      If an unexpected error occurs.
     */
    @Override
    public void createQuestion(QuestionReq questionReq) {
        try {
            if (Objects.equals(QuestionType.UNDEFINED, QuestionType.getType(questionReq.getType()))) {
                throw new BadRequestException(TExamExceptionConstant.QUESTION_E003);
            }
            if (Objects.equals(QuestionLevel.UNDEFINED, QuestionLevel.getLevel(questionReq.getLevel()))) {
                throw new BadRequestException(TExamExceptionConstant.QUESTION_E005);
            }

            Question question = questionMapper.convertReqToModel(questionReq);
            questionRepository.save(question);

            List<QuestionDetail> questionDetails = questionDetailService.resolveQuestionDetailsForQuestion(question);
            question.setQuestionDetails(questionDetails);
            questionRepository.save(question);
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a list of questions, paginated.
     *
     * @param page The page of questions to get.
     * @param size The number of questions to get per page.
     * @return A list of question DTOs.
     * @throws TExamException If an unexpected error occurs.
     */
    @Override
    public List<QuestionDto> getQuestions(int page, int size) {
        try {
            long totalElements = questionRepository.count();
            if (totalElements == 0) return Collections.emptyList();

            int correctPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int correctSize = TExamUtil.getCorrectSize(size, totalElements);

            return questionRepository.findAll(PageRequest.of(correctPage, correctSize))
                    .map(questionMapper::convertModelToDto)
                    .getContent();
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a question by ID.
     *
     * @param id The ID of the question to get.
     * @return A question DTO.
     * @throws NotFoundException If the question with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public QuestionDto getQuestionById(UUID id) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.QUESTION_E001));

            return questionMapper.convertModelToDto(question);
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a list of questions by type, paginated.
     *
     * @param type The type of question to get.
     * @param page The page of questions to get.
     * @param size The number of questions to get per page.
     * @return A list of question DTOs.
     * @throws BadRequestException If the question type is invalid.
     * @throws TExamException      If an unexpected error occurs.
     */
    @Override
    public List<QuestionDto> getQuestionsByType(String type, int page, int size) {
        try {
            QuestionType questionType = QuestionType.getType(type);
            if (Objects.equals(QuestionType.UNDEFINED, questionType)) {
                throw new BadRequestException(TExamExceptionConstant.QUESTION_E003);
            }
            long totalElements = questionRepository.countByType(questionType);
            if (totalElements == 0) return Collections.emptyList();

            int currentPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int currentSize = TExamUtil.getCorrectSize(size, totalElements);

            return questionRepository.findByType(questionType, PageRequest.of(currentPage, currentSize))
                    .map(questionMapper::convertModelToDto)
                    .toList();
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Get questions by object type ID, paginated.
     *
     * @param objectTypeId The object type ID.
     * @param page         The page number.
     * @param size         The page size.
     * @return A list of question DTOs.
     * @throws NotFoundException If no questions are found for the given object type ID.
     * @throws TExamException    If an error occurs while getting the questions.
     */
    @Override
    public List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId, int page, int size) {
        try {
            long totalElements = questionRepository.countByObjectTypeId(objectTypeId);
            if (totalElements == 0) {
                throw new NotFoundException(TExamExceptionConstant.QUESTION_E007);
            }
            int currentPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int currentSize = TExamUtil.getCorrectSize(size, totalElements);

            return questionRepository.findByObjectTypeId(objectTypeId, PageRequest.of(currentPage, currentSize))
                    .map(questionMapper::convertModelToDto)
                    .toList();
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Get questions by object type ID.
     *
     * @param objectTypeId The object type ID.
     * @return A list of question DTOs.
     * @throws NotFoundException If no questions are found for the given object type ID.
     */
    @Override
    public List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId) {
        List<Question> questions = questionRepository.findByObjectTypeId(objectTypeId);
        if (questions.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E007);
        }

        return questions.stream().map(questionMapper::convertModelToDto).toList();
    }

    /**
     * Updates a question by ID.
     *
     * @param id          The ID of the question to update.
     * @param questionDto The question DTO to update.
     * @throws BadRequestException If the question type or level is invalid.
     * @throws NotFoundException   If the question with the specified ID does not exist.
     * @throws TExamException      If an unexpected error occurs.
     */
    @Override
    public void updateQuestionById(UUID id, QuestionDto questionDto) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.QUESTION_E001));
            QuestionType questionType = QuestionType.getType(questionDto.getType());
            if (Objects.equals(QuestionType.UNDEFINED, questionType)) {
                throw new BadRequestException(TExamExceptionConstant.QUESTION_E003);
            }
            QuestionLevel questionLevel = QuestionLevel.getLevel(questionDto.getLevel());
            if (Objects.equals(QuestionLevel.UNDEFINED, questionLevel)) {
                throw new BadRequestException(TExamExceptionConstant.QUESTION_E005);
            }

            question.setType(questionType);
            question.setObjectTypeId(questionDto.getObjectTypeId());
            question.setLevel(questionLevel);
            question.setImageURLs(questionDto.getImageURLs());
            question.setAudioURL(questionDto.getAudioURL());
            question.setTranscript(questionDto.getTranscript());
            question.setQuestionDetails(questionDetailMapper.convertDTOsToModels(questionDto.getQuestionDetails()));

            questionRepository.save(question);
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Deletes a question by ID.
     *
     * @param id The ID of the question to delete.
     * @throws NotFoundException If the question with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public void deleteQuestionById(UUID id) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(TExamExceptionConstant.QUESTION_E001));

            questionRepository.deleteById(id);
            uploadService.deleteFile(question.getAudioURL());
            question.getImageURLs().parallelStream().forEach(uploadService::deleteFile);
        } catch (Exception e) {
            throw new TExamException(TExamExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Finds a question by ID.
     *
     * @param questionId The ID of the question to find.
     * @return An optional question.
     */
    @Override
    public Optional<Question> findById(UUID questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public List<Question> findByIDs(List<UUID> questionIDs) {
        return questionRepository.findAllById(questionIDs);
    }

    @Override
    public List<UUID> getQuestionIDsByQuestions(List<QuestionDto> questions) {
        return questions.parallelStream().map(QuestionDto::getId).toList();
    }

    /**
     * Finds all questions.
     *
     * @return A list of questions.
     */
    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    /**
     * Saves a question.
     *
     * @param question The question to save.
     * @return The saved question.
     */
    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
