package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PartTestDto;

import java.util.List;
import java.util.UUID;

public interface PartTestService {
    void createPartTest(PartTestDto partTestDto);
    List<PartTestDto> getPartTestsByPartId(UUID partId);
    void updatePartTest(UUID partTestId, PartTestDto partTestDto);
    void deletePartTestById(UUID partTestId);
    boolean existsById(UUID partTestId);
}
