package com.algorithms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithms.dto.SubsequenceRequest;
import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.model.Subsequence;
import com.algorithms.service.DistinctSubsequencesService;

@RestController
@RequestMapping("/api/algorithms")
public class DistinctSubsequencesController {

	@Autowired
	private DistinctSubsequencesService subsequenceService;

	@PostMapping("/distinct-subsequences")
	public SubsequenceResponse calculateDistinctSubsequences(@RequestBody SubsequenceRequest request) {

		int result = subsequenceService.calculateDistinctSubsequences(request.getSource(), request.getTarget());

		Subsequence subsequence = new Subsequence(request.getSource(), request.getTarget(), result);
		SubsequenceResponse savedSubsequence = subsequenceService.saveSubsequence(subsequence);

		return new SubsequenceResponse(savedSubsequence.getId(), savedSubsequence.getSourceInitial(),
				savedSubsequence.getTargetFinal(), savedSubsequence.getNumberSubsequence(),
				savedSubsequence.getDateCreate());
	}

	@GetMapping
	public List<SubsequenceResponse> getAllSubsequences() {
		return subsequenceService.getAllSubsequences();
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubsequenceResponse> getSubsequenceById(@PathVariable Long id) {
		Optional<SubsequenceResponse> subsequence = subsequenceService.getSubsequenceById(id);
		return subsequence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<SubsequenceResponse> createSubsequence(@RequestBody Subsequence subsequence) {
		SubsequenceResponse createdSubsequence = subsequenceService.saveSubsequence(subsequence);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSubsequence);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SubsequenceResponse> updateSubsequence(@PathVariable Long id,
			@RequestBody Subsequence subsequence) {
		SubsequenceResponse updatedSubsequence = subsequenceService.updateSubsequence(id, subsequence);
		return ResponseEntity.ok(updatedSubsequence);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSubsequence(@PathVariable Long id) {
		subsequenceService.deleteSubsequence(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
