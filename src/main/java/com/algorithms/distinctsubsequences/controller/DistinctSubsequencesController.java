package com.algorithms.distinctsubsequences.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithms.distinctsubsequences.dto.SubsequenceRequest;
import com.algorithms.distinctsubsequences.dto.SubsequenceResponse;
import com.algorithms.distinctsubsequences.service.DistinctSubsequencesService;

@RestController
@RequestMapping("/api/algorithms")
public class DistinctSubsequencesController {

	@Autowired
	private DistinctSubsequencesService service;

	@PostMapping("/distinct-subsequences")
	public SubsequenceResponse calculateDistinctSubsequences(@RequestBody SubsequenceRequest request) {
		int result = service.calculateDistinctSubsequences(request.getSource(), request.getTarget());
		return new SubsequenceResponse(result);
	}

}
