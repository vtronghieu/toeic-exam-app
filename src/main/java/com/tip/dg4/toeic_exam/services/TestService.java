package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.dto.TestDto;
import com.tip.dg4.toeic_exam.dto.requests.TestReq;

import java.util.List;
import java.util.UUID;

public interface TestService {
    void createTest(TestReq testREQ);

    List<TestDto> getTests(int page, int size);

    TestDto getTestById(UUID id);

    List<TestDto> getTestsByPartId(UUID partId);

    void updateTest(TestReq testReq);

    void deleteTestById(UUID id);

    boolean existsById(UUID id);
}
