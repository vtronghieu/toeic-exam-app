package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.dto.QuestionRequestDto;
import com.tip.dg4.toeic_exam.dto.QuestionResponseDto;
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
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public void createQuestion(QuestionRequestDto questionRequestDto) {
        QuestionType questionType = QuestionType.getType(questionRequestDto.getType());
//        if (Objects.isNull(questionType)) {
//            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
//        }
//        if (QuestionType.VOCABULARY.equals(questionType) &&
//                !vocabularyService.existsById(questionDto.getObjectTypeId())) {
//            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
//        }
//        if (QuestionType.PRACTICE.equals(questionType) &&
//                !partTestService.existsById(questionDto.getObjectTypeId())) {
//            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
//        }
//        Question question = questionMapper.convertDtoToModel(questionRequestDto);

//        questionRepository.save(question);
//        childQuestionService.createChildQuestions(question.getId(), questionDto.getQuestions());
        Question question = new Question();
        List<String> imageUrls = questionRequestDto.getImages().stream()
                                 .map(MultipartFile::getOriginalFilename).toList();

        question.setId(questionRequestDto.getId());
        question.setType(QuestionType.getType(questionRequestDto.getType()));
        question.setObjectTypeId(questionRequestDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionRequestDto.getLevel()));
        question.setTranscript(questionRequestDto.getTranscript());
        question.setAudioQuestion(questionRequestDto.getAudioQuestion());
        question.setImageUrls(imageUrls);

        questionRepository.save(question);

        questionRequestDto.getImages().forEach(multipartFile -> {
            try {
                Path directoryPath  = Paths.get("src/main/resources/images");
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath );
                }
                Path path = directoryPath.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO
            }
        });
    }

    @Override
    public List<QuestionResponseDto> getAllQuestions(HttpServletResponse response) {
        List<Question> questions = questionRepository.findAll();
        List<QuestionResponseDto> questionResponseDTOS = new CopyOnWriteArrayList<>(Collections.nCopies(questions.size(), null));

        questions.parallelStream().forEach(question -> {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionResponseDto questionResponseDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionResponseDTOS.set(questions.indexOf(question), questionResponseDTO);
            }
        });

        return questionResponseDTOS;
    }

    @Override
    public List<QuestionResponseDto> getQuestionsByObjectTypeId(UUID objectTypeId) {
        List<Question> questions = questionRepository.findByObjectTypeId(objectTypeId);
        if (questions.isEmpty()) return Collections.emptyList();

        List<QuestionResponseDto> questionResponseDTOS = new CopyOnWriteArrayList<>(Collections.nCopies(questions.size(), null));
        questions.parallelStream().forEachOrdered(question -> {
            List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());

            questionResponseDTOS.set(questions.indexOf(question), questionMapper.convertModelToDto(question, childQuestions));
        });

        return questionResponseDTOS;
    }

    @Override
    public List<QuestionResponseDto> getQuestionsByType(String type) {
        QuestionType questionType = QuestionType.getType(type);
        if (Objects.isNull(questionType)) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
        }
        List<Question> questions = questionRepository.findByType(questionType);
        List<QuestionResponseDto> questionResponseDTOS = new CopyOnWriteArrayList<>(Collections.nCopies(questions.size(), null));
        questions.parallelStream().forEachOrdered(question -> {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionResponseDto questionResponseDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionResponseDTOS.set(questions.indexOf(question), questionResponseDTO);
            }
        });

        return questionResponseDTOS;
    }

    @Override
    public List<QuestionResponseDto> getQuestionsByObjectTypeIds(List<UUID> objectTypeIds) {
        List<Question> questions = new CopyOnWriteArrayList<>();
        objectTypeIds.parallelStream().forEach(objectTypeId -> {
            List<Question> localQuestion = questionRepository.findByObjectTypeId(objectTypeId);
            questions.addAll(localQuestion);
        });
        List<QuestionResponseDto> questionResponseDTOS = new CopyOnWriteArrayList<>(Collections.nCopies(questions.size(), null));
        questions.parallelStream().forEach(question -> {
            Optional<Question> optionalQuestion = questionRepository.findById(question.getId());
            if (optionalQuestion.isPresent()) {
                List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(question.getId());
                QuestionResponseDto questionResponseDTO = questionMapper.convertModelToDto(optionalQuestion.get(), childQuestions);

                questionResponseDTOS.set(questions.indexOf(question), questionResponseDTO);
            }
        });

        return questionResponseDTOS;
    }

    @Override
    public QuestionResponseDto getQuestionById(UUID questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }
        Question question = optionalQuestion.get();
        List<ChildQuestion> childQuestions = childQuestionService.getChildQuestionsByQuestionId(questionId);

        return questionMapper.convertModelToDto(question, childQuestions);
    }

    @Override
    public void updateQuestion(UUID questionId, QuestionResponseDto questionResponseDto) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            throw new NotFoundException(TExamExceptionConstant.QUESTION_E006);
        }
        QuestionType questionDtoType = QuestionType.getType(questionResponseDto.getType());
        if (Objects.isNull(questionDtoType)) {
            throw new BadRequestException(TExamExceptionConstant.QUESTION_E002);
        }
        if (QuestionType.VOCABULARY.equals(questionDtoType) &&
                !vocabularyService.existsById(questionResponseDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.VOCABULARY_E003);
        }
        if (QuestionType.PRACTICE.equals(questionDtoType) &&
                !partTestService.existsById(questionResponseDto.getObjectTypeId())) {
            throw new NotFoundException(TExamExceptionConstant.PART_TEST_E003);
        }
        Question question = optionalQuestion.get();
        question.setType(questionDtoType);
        question.setObjectTypeId(questionResponseDto.getObjectTypeId());
        question.setLevel(QuestionLevel.getLevel(questionResponseDto.getLevel()));
        question.setAudioQuestion(questionResponseDto.getAudioQuestion());
        question.setImageUrls(questionResponseDto.getImageUrls());

        childQuestionService.updateChildQuestion(question.getId(), questionResponseDto.getQuestions());
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
