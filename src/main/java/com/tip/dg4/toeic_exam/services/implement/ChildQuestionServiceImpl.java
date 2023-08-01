package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.dto.ChildQuestionDto;
import com.tip.dg4.toeic_exam.mappers.ChildQuestionMapper;
import com.tip.dg4.toeic_exam.models.ChildQuestion;
import com.tip.dg4.toeic_exam.repositories.ChildQuestionRepository;
import com.tip.dg4.toeic_exam.services.ChildQuestionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class ChildQuestionServiceImpl implements ChildQuestionService {
    private final ChildQuestionRepository childQuestionRepository;
    private final ChildQuestionMapper childQuestionMapper;

    public ChildQuestionServiceImpl(ChildQuestionRepository childQuestionRepository,
                                    ChildQuestionMapper childQuestionMapper) {
        this.childQuestionRepository = childQuestionRepository;
        this.childQuestionMapper = childQuestionMapper;
    }

    @Override
    public void createChildQuestions(UUID questionId, List<ChildQuestionDto> childQuestionDTOs) {
        List<ChildQuestion> childQuestions = childQuestionDTOs.stream()
                                             .map(childQuestionMapper::convertDtoToModel).toList();

        if (childQuestions.isEmpty()) return;
        for (ChildQuestion childQuestion : childQuestions) {
            childQuestion.setQuestionId(questionId);
            childQuestionRepository.save(childQuestion);
        }
    }

    @Override
    public List<ChildQuestion> getChildQuestionsByQuestionId(UUID questionId) {
        return childQuestionRepository.findByQuestionId(questionId);
    }

    @Override
    public void updateChildQuestion(UUID questionId, List<ChildQuestionDto> childQuestionDTOs) {
        if (childQuestionDTOs.isEmpty()) return;
        this.deleteChildQuestionsByQuestionId(questionId);
        this.createChildQuestions(questionId, childQuestionDTOs);
//        List<ChildQuestion> childQuestions = childQuestionRepository.findByQuestionId(questionId);
//        for (int i = 0; i < childQuestions.size(); i++) {
//            Optional<ChildQuestion> optionalChildQuestion = childQuestionRepository.findById(childQuestions.get(i).getId());
//            if (optionalChildQuestion.isPresent()) {
//                ChildQuestion childQuestion = optionalChildQuestion.get();
//                ChildQuestionDto childQuestionDTO = childQuestionDTOs.get(i);
//
//                childQuestion.setQuestionId(childQuestionDTO.getQuestionId());
//                childQuestion.setTextQuestion(childQuestionDTO.getTextQuestion());
//                childQuestion.setAnswerA(childQuestionDTO.getAnswerA());
//                childQuestion.setAnswerB(childQuestionDTO.getAnswerB());
//                childQuestion.setAnswerC(childQuestionDTO.getAnswerC());
//                childQuestion.setAnswerD(childQuestionDTO.getAnswerD());
//                childQuestion.setCorrectAnswer(childQuestionDTO.getCorrectAnswer());
//
//                childQuestionRepository.save(childQuestion);
//            }
//        }
    }

    @Override
    public void deleteChildQuestionsByQuestionId(UUID questionId) {
        childQuestionRepository.deleteByQuestionId(questionId);
    }
}
