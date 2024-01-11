package com.tip.dg4.toeic_exam.mappers;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.dto.StatisticDto;
import com.tip.dg4.toeic_exam.exceptions.NotFoundException;
import com.tip.dg4.toeic_exam.models.Statistic;
import com.tip.dg4.toeic_exam.models.User;
import com.tip.dg4.toeic_exam.services.UserService;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticMapper {
    private final UserService userService;

    public StatisticDto convertModelToDto(Statistic statistic) {
        User user = userService.findById(statistic.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionConstant.USER_E001));

        return StatisticDto.builder()
                .id(statistic.getId())
                .username(user.getUsername())
                .fullName(TExamUtil.getFullName(user))
                .createDate(statistic.getCreateDate())
                .nearestTestDate(statistic.getNearestTestDate())
                .practiceProgress(statistic.getPracticeProgress())
                .build();
    }
}
