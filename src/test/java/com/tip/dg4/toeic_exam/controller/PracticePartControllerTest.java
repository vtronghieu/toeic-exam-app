package com.tip.dg4.toeic_exam.controller;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.PracticePartController;
import com.tip.dg4.toeic_exam.dto.PracticePartWithoutLessonsAndTestsDto;
import com.tip.dg4.toeic_exam.services.PracticePartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PracticePartControllerTest {

	@Mock
	private PracticePartService practicePartService;

	@InjectMocks
	private PracticePartController practicePartController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreatePartWithoutLessonsAndTests_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		PracticePartWithoutLessonsAndTestsDto partWithoutLessonsAndTestsDto = new PracticePartWithoutLessonsAndTestsDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S001),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.createPartWithoutLessonsAndTests(practiceId, partWithoutLessonsAndTestsDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).createPartWithoutLessonsAndTests(practiceId, partWithoutLessonsAndTestsDto);
	}

	@Test
	public void testGetPartsWithoutLessonsAndTestsByPracticeId_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S002),
				HttpStatus.OK
		);
		when(practicePartService.getPartsWithoutLessonsAndTestsByPracticeId(practiceId)).thenReturn(Collections.singletonList(new PracticePartWithoutLessonsAndTestsDto()));

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.getPartsWithoutLessonsAndTestsByPracticeId(practiceId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).getPartsWithoutLessonsAndTestsByPracticeId(practiceId);
	}

	@Test
	public void testUpdatePartWithoutLessonsAndTests_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		UUID practicePartId = UUID.randomUUID();
		PracticePartWithoutLessonsAndTestsDto dtoWithoutLessonsAndTests = new PracticePartWithoutLessonsAndTestsDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S003),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.updatePartWithoutLessonsAndTests(practiceId, practicePartId, dtoWithoutLessonsAndTests);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).updatePartWithoutLessonsAndTests(practiceId, practicePartId, dtoWithoutLessonsAndTests);
	}

	@Test
	public void testDeletePartById_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		UUID practicePartId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S004),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.deletePartById(practiceId, practicePartId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).deletePartById(practiceId, practicePartId);
	}

	@Test
	public void testCreatePartWithoutLessonsAndTests_WithServiceException_ReturnsErrorResponse() {
	}

	@Test
	public void testUpdatePartWithoutLessonsAndTests_WithServiceException_ReturnsErrorResponse() {
	}

}
