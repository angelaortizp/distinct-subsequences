package com.algorithms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.algorithms.controller.DistinctSubsequencesController;
import com.algorithms.dto.SubsequenceRequest;
import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.model.Subsequence;
import com.algorithms.service.DistinctSubsequencesService;
import com.algorithms.util.exception.ErrorResponse;

class DistinctSubsequencesControllerTests {

	@Mock
	private DistinctSubsequencesService subsequenceService;

	@InjectMocks
	private DistinctSubsequencesController controller;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createSubsequence_ValidInput_ReturnsCreated() {
		SubsequenceRequest request = new SubsequenceRequest("abcabc", "abc", 7);
		SubsequenceResponse response = new SubsequenceResponse(1L, "abcabc", "abc", 7, null);

		when(subsequenceService.calculateDistinctSubsequences(anyString(), anyString())).thenReturn(7);
		when(subsequenceService.saveSubsequence(any(Subsequence.class))).thenReturn(response);
		when(subsequenceService.existsSimilarSubsequence(anyString(), anyString(), anyInt())).thenReturn(false);

		ResponseEntity<?> result = controller.createSubsequence(request);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertTrue(result.getBody() instanceof SubsequenceResponse);
		assertEquals(response, result.getBody());
	}

	@Test
	void createSubsequence_InvalidCalculation_ReturnsBadRequest() {
		SubsequenceRequest request = new SubsequenceRequest("abcabc", "abc", 7);

		when(subsequenceService.calculateDistinctSubsequences(anyString(), anyString())).thenReturn(6);

		ResponseEntity<?> result = controller.createSubsequence(request);

		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertTrue(result.getBody() instanceof ErrorResponse);
	}

	@Test
	void createSubsequence_ExistingSimilarSubsequence_ReturnsConflict() {
		SubsequenceRequest request = new SubsequenceRequest("abcabc", "abc", 7);

		when(subsequenceService.calculateDistinctSubsequences(anyString(), anyString())).thenReturn(7);
		when(subsequenceService.existsSimilarSubsequence(anyString(), anyString(), anyInt())).thenReturn(true);

		ResponseEntity<?> result = controller.createSubsequence(request);

		assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
		assertTrue(result.getBody() instanceof ErrorResponse);
	}

	@Test
	void updateSubsequence_ValidInput_ReturnsOk() {
		SubsequenceRequest request = new SubsequenceRequest("abcabc", "abc", 7);
		SubsequenceResponse response = new SubsequenceResponse(1L, "abcabc", "abc", 7, null);

		when(subsequenceService.calculateDistinctSubsequences(anyString(), anyString())).thenReturn(7);
		when(subsequenceService.updateSubsequence(anyLong(), any(Subsequence.class))).thenReturn(response);
		when(subsequenceService.existsSimilarSubsequence(anyString(), anyString(), anyInt())).thenReturn(false);

		ResponseEntity<?> result = controller.updateSubsequence(1L, request);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.getBody() instanceof SubsequenceResponse);
		assertEquals(response, result.getBody());
	}
}