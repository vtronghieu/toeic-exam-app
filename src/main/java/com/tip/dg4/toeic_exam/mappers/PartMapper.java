package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.dto.LessonDto;
import com.tip.dg4.toeic_exam.dto.TestDto;
import com.tip.dg4.toeic_exam.dto.PartDto;
import com.tip.dg4.toeic_exam.dto.requests.PartReq;
import com.tip.dg4.toeic_exam.models.Lesson;
import com.tip.dg4.toeic_exam.models.Test;
import com.tip.dg4.toeic_exam.models.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PartMapper {
    private final LessonMapper lessonMapper;
    private final TestMapper testMapper;

    public Part convertReqToModel(PartReq partREQ) {
        return Part.builder()
                .id(partREQ.getId())
                .practiceId(partREQ.getPracticeId())
                .name(partREQ.getName())
                .imageURL(partREQ.getImageURL())
                .description(partREQ.getDescription())
                .build();
    }

    public Part convertDtoToModel(PartDto partDto) {
        List<Lesson> lessons = lessonMapper.convertDTOsToModels(partDto.getLessons());
        List<Test> tests = testMapper.convertDTOsToModels(partDto.getTests());

        return Part.builder()
                .id(partDto.getId())
                .practiceId(partDto.getPracticeId())
                .name(partDto.getName())
                .imageURL(partDto.getImageURL())
                .description(partDto.getDescription())
                .lessons(lessons)
                .tests(tests)
                .build();
    }

    public PartDto convertModelToDto(Part part) {
        List<LessonDto> lessonDTOs = lessonMapper.convertModelsToDTOs(part.getLessons());
        List<TestDto> testDTOs = testMapper.convertModelsToDTOs(part.getTests());

        return PartDto.builder()
                .id(part.getId())
                .practiceId(part.getPracticeId())
                .name(part.getName())
                .imageURL(part.getImageURL())
                .description(part.getDescription())
                .lessons(lessonDTOs)
                .tests(testDTOs)
                .build();
    }

    public List<PartDto> convertModelsToDTOs(List<Part> parts) {
        return Optional.ofNullable(parts).orElse(Collections.emptyList()).stream()
                .map(this::convertModelToDto)
                .toList();
    }

    public List<Part> convertDTOsToModels(List<PartDto> partDTOs) {
        return Optional.ofNullable(partDTOs).orElse(Collections.emptyList()).stream()
                .map(this::convertDtoToModel)
                .toList();
    }
}
