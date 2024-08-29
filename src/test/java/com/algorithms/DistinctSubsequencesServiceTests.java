package com.algorithms;

import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.model.Subsequence;
import com.algorithms.repository.SubsequenceRepository;
import com.algorithms.service.DistinctSubsequencesService;
import com.algorithms.util.exception.InvalidInputException;
import com.algorithms.util.exception.SubsequenceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistinctSubsequencesServiceTests {

	@Mock
	private SubsequenceRepository subsequenceRepository;

	@InjectMocks
	private DistinctSubsequencesService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void calculateDistinctSubsequences_ValidInput_ReturnsCorrectCount() {
		String source = "rabbbit";
		String target = "rabbit";

		int result = service.calculateDistinctSubsequences(source, target);

		assertEquals(3, result);
	}

	@Test
	void calculateDistinctSubsequences_InvalidLength_ThrowsException() {
		String source = "a".repeat(1001);
		String target = "a";

		assertThrows(InvalidInputException.class, () -> {
			service.calculateDistinctSubsequences(source, target);
		});
	}

	@Test
	void calculateDistinctSubsequences_InvalidCharacters_ThrowsException() {
		String source = "abc123";
		String target = "abc";

		assertThrows(InvalidInputException.class, () -> {
			service.calculateDistinctSubsequences(source, target);
		});
	}

	@Test
	void saveSubsequence_ValidInput_ReturnsSavedSubsequence() {

		Long id = 10L;
		LocalDateTime creationTime = LocalDateTime.now();
		Subsequence inputSubsequence = new Subsequence("abcabc", "abc", 7);

		Subsequence savedSubsequence = new Subsequence("abcabc", "abc", 7);
		savedSubsequence.setId(id);
		savedSubsequence.setDateCreate(creationTime);

		when(subsequenceRepository.save(any(Subsequence.class))).thenReturn(savedSubsequence);

		SubsequenceResponse result = service.saveSubsequence(inputSubsequence);

		assertNotNull(result);
		assertEquals(id, result.getId());
		assertEquals("abcabc", result.getSourceInitial());
		assertEquals("abc", result.getTargetFinal());
		assertEquals(7, result.getNumberSubsequence());
		assertEquals(creationTime, result.getDateCreate());

		verify(subsequenceRepository).save(any(Subsequence.class));
	}

	@Test
	void existsSimilarSubsequence_ExistingSubsequence_ReturnsTrue() {
		String source = "rabbbit";
		String target = "rabbit";
		int count = 3;
		when(subsequenceRepository.existsBySourceInitialAndTargetFinalAndNumberSubsequence(source, target, count))
				.thenReturn(true);

		boolean result = service.existsSimilarSubsequence(source, target, count);

		assertTrue(result);
	}

}
