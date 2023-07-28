package com.tip.dg4.toeic_exam.controller;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.PracticePartController;
import com.tip.dg4.toeic_exam.dto.PracticePartDto;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	public void testCreatePart_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		PracticePartDto practicePartDto = new PracticePartDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S001),
				HttpStatus.CREATED
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.createPracticePart(practicePartDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).createPracticePart(practicePartDto);
	}

	@Test
	public void testGetPartsByPracticeId_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S002),
				HttpStatus.OK
		);
		when(practicePartService.getPracticePartsByPracticeId(practiceId)).thenReturn(Collections.singletonList(new PracticePartDto()));

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.getPracticePartsByPracticeId(practiceId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertNotNull(actualResponse.getBody());
		verify(practicePartService, times(1)).getPracticePartsByPracticeId(practiceId);
	}

	@Test
	public void testUpdatePart_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		UUID practicePartId = UUID.randomUUID();
		PracticePartDto practicePartDto = new PracticePartDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S003),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.updatePracticePart(practicePartId, practicePartDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).updatePracticePart(practicePartId, practicePartDto);
	}

	@Test
	public void testDeletePartById_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practicePartId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_PART_S004),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practicePartController.deletePracticePartById(practicePartId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practicePartService, times(1)).deletePracticePartById(practicePartId);
	}

	@Test
	public void testCreatePart_WithServiceException_ReturnsErrorResponse() {
	}

	@Test
	public void testUpdatePart_WithServiceException_ReturnsErrorResponse() {
	}

}
