package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.question.QuestionDetailDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.exceptions.TExamException;
import com.tip.dg4.toeic_exam.mappers.QuestionDetailMapper;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionDetail;
import com.tip.dg4.toeic_exam.services.QuestionDetailService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class QuestionDetailServiceImpl implements QuestionDetailService {
    @Lazy
    private final QuestionService questionService;
    private final QuestionDetailMapper questionDetailMapper;

    /**
     * Creates a list of question details for the given question.
     *
     * @param questionDetailDTOs A list of question detail DTOs.
     * @throws BadRequestException If the list of question detail DTOs is empty.
     * @throws NotFoundException   If the question with the specified ID does not exist.
     * @throws TExamException      If an unexpected error occurs.
     */
    @Override
    public void createQuestionDetails(List<QuestionDetailDto> questionDetailDTOs) {
        try {
            if (questionDetailDTOs.isEmpty()) {
                throw new BadRequestException(ExceptionConstant.QUESTION_DETAIL_E005);
            }
            Question question = questionService.findById(questionDetailDTOs.get(0).getQuestionId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_E001));
            List<QuestionDetail> questionDetails = questionDetailMapper.convertDTOsToModels(questionDetailDTOs);

            question.getQuestionDetails().addAll(questionDetails);
            questionService.save(question);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a list of question detail DTOs for the specified page and size.
     *
     * @param page The page of question detailDTOs to return.
     * @param size The number of question detail DTOs to return per page.
     * @return A list of question detail DTOs.
     * @throws TExamException If an unexpected error occurs.
     */
    @Override
    public List<QuestionDetailDto> getQuestionDetails(int page, int size) {
        try {
            List<QuestionDetail> questionDetails = this.findAll();
            long totalElements = questionDetails.size();
            int correctPage = TExamUtil.getCorrectPage(page, size, totalElements);
            int correctSize = TExamUtil.getCorrectSize(size, totalElements);

            return TExamUtil.paginateList(questionDetails, PageRequest.of(correctPage, correctSize))
                    .map(questionDetailMapper::convertModelToDto)
                    .getContent();
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a list of question detail DTOs for the given question ID.
     *
     * @param questionId The ID of the question.
     * @return A list of question detail DTOs.
     * @throws NotFoundException If the question with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public List<QuestionDetailDto> getQuestionDetailsByQuestionId(UUID questionId) {
        try {
            Question question = questionService.findById(questionId)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_E001));

            return question.getQuestionDetails().stream()
                    .map(questionDetailMapper::convertModelToDto)
                    .toList();
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Gets a question detail DTO for the given question detail ID.
     *
     * @param id The ID of the question detail.
     * @return A question detail DTO.
     * @throws NotFoundException If the question detail with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public QuestionDetailDto getQuestionDetailById(UUID id) {
        try {
            QuestionDetail questionDetail = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_DETAIL_E006));

            return questionDetailMapper.convertModelToDto(questionDetail);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Updates the given question detail.
     *
     * @param questionDetailDto The question detail DTO to update.
     * @throws NotFoundException If the question detail with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public void updateQuestionDetail(QuestionDetailDto questionDetailDto) {
        try {
            if (!existsById(questionDetailDto.getId())) {
                throw new NotFoundException(ExceptionConstant.QUESTION_DETAIL_E006);
            }
            Question question = questionService.findById(questionDetailDto.getQuestionId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_E001));

            question.getQuestionDetails().stream()
                    .filter(questionDetail -> questionDetailDto.getId().equals(questionDetail.getId()))
                    .findFirst()
                    .ifPresent(questionDetail -> {
                        questionDetail.setQuestionId(questionDetailDto.getQuestionId());
                        questionDetail.setContentQuestion(questionDetailDto.getContentQuestion());
                        questionDetail.setAnswers(questionDetailDto.getAnswers());
                        questionDetail.setCorrectAnswer(questionDetailDto.getCorrectAnswer());
                    });
            questionService.save(question);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Deletes a question detail by the given ID.
     *
     * @param id The ID of the question detail to delete.
     * @throws NotFoundException If the question detail with the specified ID does not exist.
     * @throws TExamException    If an unexpected error occurs.
     */
    @Override
    public void deleteQuestionDetailById(UUID id) {
        try {
            QuestionDetail questionDetail = this.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_DETAIL_E006));
            Question question = questionService.findById(questionDetail.getQuestionId())
                    .orElseThrow(() -> new NotFoundException(ExceptionConstant.QUESTION_E001));

            question.getQuestionDetails().remove(questionDetail);
            questionService.save(question);
        } catch (Exception e) {
            throw new TExamException(ExceptionConstant.TEXAM_E001, e);
        }
    }

    /**
     * Resolves the question details for the given question.
     * This method returns a list of all question details associated with the given question.
     * The question details will be resolved, meaning that their question ID will be set to the ID of the given question.
     *
     * @param question question the question for which to resolve the question details
     * @return a list of all question details associated with the given question.
     */
    @Override
    public List<QuestionDetail> resolveQuestionDetailsForQuestion(Question question) {
        return question.getQuestionDetails().stream()
                .peek(questionDetail -> questionDetail.setQuestionId(question.getId()))
                .toList();
    }

    /**
     * Finds a question detail by the given ID.
     *
     * @param id The ID of the question detail to find.
     * @return An optional question detail DTO.
     */
    @Override
    public Optional<QuestionDetail> findById(UUID id) {
        return this.findAll().stream()
                .filter(questionDetail -> id.equals(questionDetail.getId()))
                .findFirst();
    }

    /**
     * Checks if a question detail exists for the given ID.
     *
     * @param id The ID of the question detail to check.
     * @return True if the question detail exists, false otherwise.
     */
    @Override
    public boolean existsById(UUID id) {
        return this.findAll().stream().anyMatch(questionDetail -> id.equals(questionDetail.getId()));
    }

    /**
     * Finds all question details.
     *
     * @return A list of question details.
     */
    @Override
    public List<QuestionDetail> findAll() {
        List<QuestionDetail> questionDetails = questionService.findAll().stream()
                .flatMap(question -> question.getQuestionDetails().stream()).toList();

        return Optional.of(questionDetails).orElse(new ArrayList<>());
    }

    /**
     * Finds all question details for the given question ID.
     *
     * @param questionId the question ID
     * @return a list of question details, or an empty list if no question details can be found
     */
    @Override
    public List<QuestionDetail> findByQuestionId(UUID questionId) {
        List<QuestionDetail> questionDetails = this.findAll();

        return questionDetails.stream()
                .filter(questionDetail -> questionId.equals(questionDetail.getQuestionId()))
                .toList();
    }

    /*
    @Override
    public void deleteChildQuestionsByQuestionId(UUID questionId) {
        childQuestionRepository.deleteByQuestionId(questionId);
    }*/
}
