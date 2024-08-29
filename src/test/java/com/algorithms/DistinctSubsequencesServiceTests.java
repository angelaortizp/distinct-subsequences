package com.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.factory.SubsequenceFactory;
import com.algorithms.model.Subsequence;
import com.algorithms.repository.SubsequenceRepository;
import com.algorithms.service.DistinctSubsequencesService;
import com.algorithms.util.exception.InvalidInputException;

class DistinctSubsequencesServiceTests {

	@Mock
	private SubsequenceRepository subsequenceRepository;
	
	@Mock
	private SubsequenceFactory subsequenceFactory;

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
	    String sourceInitial = "abcabc";
	    String targetFinal = "abc";
	    int numberSubsequence = 7;

	    Subsequence savedSubsequence = new Subsequence(sourceInitial, targetFinal, numberSubsequence);
	    savedSubsequence.setId(id);
	    savedSubsequence.setDateCreate(creationTime);

	    
	    when(subsequenceFactory.createSubsequence(sourceInitial, targetFinal, numberSubsequence))
	        .thenReturn(new Subsequence(sourceInitial, targetFinal, numberSubsequence));

	    when(subsequenceRepository.save(any(Subsequence.class))).thenReturn(savedSubsequence);

	   
	    SubsequenceResponse result = service.saveSubsequence(sourceInitial, targetFinal, numberSubsequence);

	   
	    assertNotNull(result);
	    assertEquals(id, result.getId());
	    assertEquals(sourceInitial, result.getSourceInitial());
	    assertEquals(targetFinal, result.getTargetFinal());
	    assertEquals(numberSubsequence, result.getNumberSubsequence());
	    assertEquals(creationTime, result.getDateCreate());

	  
	    verify(subsequenceFactory).createSubsequence(sourceInitial, targetFinal, numberSubsequence);
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
