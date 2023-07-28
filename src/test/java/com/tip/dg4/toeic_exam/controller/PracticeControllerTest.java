package com.tip.dg4.toeic_exam.controller;

import com.tip.dg4.toeic_exam.common.constants.TExamSuccessfulConstant;
import com.tip.dg4.toeic_exam.common.responses.ResponseData;
import com.tip.dg4.toeic_exam.controllers.PracticeController;
import com.tip.dg4.toeic_exam.dto.PracticeDto;
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
	public void testCreatePractice_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		PracticeDto practiceDto = new PracticeDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S002),
				HttpStatus.CREATED
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.createPractice(practiceDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).createPractice(practiceDto);
	}

	@Test
	public void testGetAllPractices_ReturnsSuccessResponse() {
		// Arrange
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S001, Collections.emptyList()),
				HttpStatus.OK
		);
		when(practiceService.getAllPractices()).thenReturn(Collections.emptyList());

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.getAllPractices();

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).getAllPractices();
	}

	@Test
	public void testUpdatePractice_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		PracticeDto practiceDto = new PracticeDto();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S003),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.updatePractice(practiceId, practiceDto);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).updatePractice(practiceId, practiceDto);
	}

	@Test
	public void testDeletePractice_WithValidRequest_ReturnsSuccessResponse() {
		// Arrange
		UUID practiceId = UUID.randomUUID();
		ResponseEntity<ResponseData> expectedResponse = new ResponseEntity<>(
				new ResponseData(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), TExamSuccessfulConstant.PRACTICE_S004),
				HttpStatus.OK
		);

		// Act
		ResponseEntity<ResponseData> actualResponse = practiceController.deletePractice(practiceId);

		// Assert
		assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
		assertEquals(expectedResponse.getBody(), actualResponse.getBody());
		verify(practiceService, times(1)).deletePractice(practiceId);
	}

	@Test
	public void testCreatePart_WithServiceException_ReturnsErrorResponse() {
	}

	@Test
	public void testUpdatePart_WithServiceException_ReturnsErrorResponse() {
	}

}
