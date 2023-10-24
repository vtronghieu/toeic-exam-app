package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.PartDto;
import com.tip.dg4.toeic_exam.dto.requests.PartReq;
import com.tip.dg4.toeic_exam.models.Practice;
import com.tip.dg4.toeic_exam.models.Part;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PartService {
    void createPart(PartReq partREQ);

    List<PartDto> getPartsByPracticeId(UUID practiceId);

    void updatePart(PartReq partREQ);

    void deletePartById(UUID id);

    void deletePartByPracticeIdAndPartId(UUID practiceId, UUID partId);

    List<Part> findAll();

    Optional<Part> findById(UUID id);

    Optional<Part> findByPracticeAndId(Practice practice, UUID id);
}
