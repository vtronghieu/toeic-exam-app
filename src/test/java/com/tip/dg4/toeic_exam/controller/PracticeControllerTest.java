package com.tip.dg4.toeic_exam.controller;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.PracticeController;
import com.tip.dg4.toeic_exam.dto.PracticeWithoutPartsDto;
import com.tip.dg4.toeic_exam.services.PracticeService;
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

class PracticeControllerTest {

	@Mock
	private PracticeService practiceService;

	@InjectMocks
	private PracticeController practiceController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreatePracticeWithoutParts_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		PracticeWithoutPartsDto practiceWithoutPartsDto = new PracticeWithoutPartsDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S002),
				HttpStatus.CREATED
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.createPracticeWithoutParts(practiceWithoutPartsDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).createPracticeWithoutParts(practiceWithoutPartsDto);
	}

	@Test
	public void testGetAllPracticesWithoutParts_ReturnsSuccessResponse() {
		// Arrange
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S001, Collections.emptyList()),
				HttpStatus.OK
		);
		when(practiceService.getAllPracticesWithoutParts()).thenReturn(Collections.emptyList());

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.getAllPracticesWithoutParts();

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).getAllPracticesWithoutParts();
	}

	@Test
	public void testUpdatePracticeWithoutParts_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		PracticeWithoutPartsDto practiceWithoutPartsDto = new PracticeWithoutPartsDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S003),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.updatePracticeWithoutParts(practiceId, practiceWithoutPartsDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).updatePracticeWithoutParts(practiceId, practiceWithoutPartsDto);
	}

	@Test
	public void testDeletePracticeWithoutParts_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S004),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.deletePracticeWithoutParts(practiceId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).deletePracticeWithoutParts(practiceId);
	}

	@Test
	public void testCreatePartWithoutPart_WithServiceException_ReturnsErrorResponse() {
	}

	@Test
	public void testUpdatePartWithoutPart_WithServiceException_ReturnsErrorResponse() {
	}

}
