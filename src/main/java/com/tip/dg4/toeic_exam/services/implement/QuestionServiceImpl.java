package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.QuestionMapper;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionLevel;
import com.tip.dg4.toeic_exam.models.QuestionType;
import com.tip.dg4.toeic_exam.repositories.QuestionRepository;
import com.tip.dg4.toeic_exam.services.ChildQuestionService;
import com.tip.dg4.toeic_exam.services.PartTestService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final VocabularyService vocabularyService;
    private final PartTestService partTestService;
    private final ChildQuestionService childQuestionService;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               QuestionMapper questionMapper,
                               VocabularyService vocabularyService,
                               PartTestService partTestService,
                               ChildQuestionService childQuestionService) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.vocabularyService = vocabularyService;
        this.partTestService = partTestService;
        this.childQuestionService = childQuestionService;
    }

    @Override
    public void createQuestion(QuestionDto questionDto) {
        QuestionType questionType = QuestionType.getType(questionDto.getType());
        if (Objects.isNull(questionType)) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
        }
        if (QuestionType.VOCABULARY.equals(questionType) &&
                !vocabularyService.existsById(questionDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }
        if (QuestionType.PRACTICE.equals(questionType) &&
                !partTestService.existsById(questionDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
        }
        Question question = questionMapper.convertDtoToModel(questionDto);
        questionRepository.save(question);
        childQuestionService.createChildQuestions(question.getId(), questionDto.getQuestions());
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDTOs = new CopyOnWriteArrayList<>();

        for (Question question : questions) {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionDto questionDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionDTOs.add(questionDTO);
            }
        }

        return questionDTOs;
    }

    @Override
    public List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId) {
        List<Question> questions = questionRepository.findByObjectTypeId(objectTypeId);
        if (questions.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }
        List<QuestionDto> questionDTOs = new ArrayList<>();

        for (Question question : questions) {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionDto questionDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                for (ChildQuestionDto childQuestionDto : questionDTO.getQuestions()) {
                    childQuestionDto.setCorrectAnswer(null);
                }
                questionDTOs.add(questionDTO);
            }
        }

        return questionDTOs;
    }

    @Override
    public List<QuestionDto> getQuestionsByType(String type) {
        QuestionType questionType = QuestionType.getType(type);
        if (Objects.isNull(questionType)) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
        }
        List<Question> questions = questionRepository.findByType(questionType);
        List<QuestionDto> questionDTOs = new ArrayList<>();

        for (Question question : questions) {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionDto questionDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionDTOs.add(questionDTO);
            }
        }

        return questionDTOs;
    }

    @Override
    public List<QuestionDto> getQuestionsByObjectTypeIds(List<UUID> objectTypeIds) {
        List<Question> questions = new ArrayList<>();
        for (UUID objectTypeId : objectTypeIds) {
            List<Question> localQuestions = questionRepository.findByObjectTypeId(objectTypeId);
            if (!localQuestions.isEmpty()) {
                questions.addAll(localQuestions);
            }
        }

        List<QuestionDto> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionDto questionDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionDTOs.add(questionDTO);
            }
        }

        return questionDTOs;
    }

    @Override
    public void updateQuestion(UUID questionId, QuestionDto questionDto) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }
        QuestionType questionDtoType = QuestionType.getType(questionDto.getType());
        if (Objects.isNull(questionDtoType)) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
        }
        if (QuestionType.VOCABULARY.equals(questionDtoType) &&
                !vocabularyService.existsById(questionDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }
        if (QuestionType.PRACTICE.equals(questionDtoType) &&
                !partTestService.existsById(questionDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
        }
        Question question = optionalQuestion.get();
        question.setType(questionDtoType);
        question.setObjectTypeId(questionDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionDto.getLevel()));
        question.setAudioQuestion(questionDto.getAudioQuestion());
        question.setImages(questionDto.getImages());

        childQuestionService.updateChildQuestion(question.getId(), questionDto.getQuestions());
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }

        childQuestionService.deleteChildQuestionsByQuestionId(questionId);
        questionRepository.deleteById(questionId);
    }

    @Override
    public boolean existsById(UUID questionId) {
        return questionRepository.existsById(questionId);
    }

    @Override
    public Optional<Question> findById(UUID questionId) {
        return questionRepository.findById(questionId);
    }
}
