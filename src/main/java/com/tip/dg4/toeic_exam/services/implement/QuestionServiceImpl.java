package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.QuestionDto;
import com.tip.dg4.toeic_exam.exceptions.BadRequestException;
import com.tip.dg4.toeic_exam.exceptions.ConflictException;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.mappers.QuestionMapper;
import com.tip.dg4.toeic_exam.models.Question;
import com.tip.dg4.toeic_exam.models.QuestionLevel;
import com.tip.dg4.toeic_exam.models.QuestionType;
import com.tip.dg4.toeic_exam.repositories.QuestionRepository;
import com.tip.dg4.toeic_exam.services.PartTestService;
import com.tip.dg4.toeic_exam.services.QuestionService;
import com.tip.dg4.toeic_exam.services.VocabularyService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final VocabularyService vocabularyService;
    private final PartTestService partTestService;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               QuestionMapper questionMapper,
                               VocabularyService vocabularyService,
                               PartTestService partTestService) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.vocabularyService = vocabularyService;
        this.partTestService = partTestService;
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
    }

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(questionMapper::convertModelToDto).toList();
    }

    @Override
    public List<QuestionDto> getQuestionsByObjectTypeId(UUID objectTypeId) {
        List<Question> questions = questionRepository.findByObjectTypeId(objectTypeId);
        if (questions.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }

        return questions.stream().map(questionMapper::convertModelToDto).toList();
    }

    @Override
    public Optional<Question> findByTypeAndId(QuestionType questionType, UUID questionId) {
        return questionRepository.findAll().stream().
               filter(question -> questionType.equals(question.getType()) &&
                                  questionId.equals(question.getId()))
               .findFirst();
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
        if (TExamUtil.isVocabularyTypeOrGrammarType(questionDtoType) &&
                !Objects.equals(question.getTextQuestion(), questionDto.getTextQuestion()) &&
                existsByTypeAndTextQuestion(questionDtoType, questionDto.getTextQuestion())) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E002);
        }
        question.setType(questionDtoType);
        question.setObjectTypeId(questionDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionDto.getLevel()));
        question.setTextQuestion(questionDto.getTextQuestion());
        question.setAudioQuestion(questionDto.getAudioQuestion());
        question.setImages(questionDto.getImages());
        question.setOptionAnswers(questionDto.getOptionAnswers());

        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }
        questionRepository.deleteById(questionId);
    }

    private boolean existsByTypeAndTextQuestion(QuestionType questionType, String textQuestion) {
        return questionRepository.findAll().stream()
                .anyMatch(question -> questionType.equals(question.getType()) &&
                                      textQuestion.equals(question.getTextQuestion()));
    }
}
