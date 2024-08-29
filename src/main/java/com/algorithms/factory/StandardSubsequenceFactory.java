package com.algorithms.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algorithms.model.Subsequence;

public class StandardSubsequenceFactory extends SubsequenceFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(StandardSubsequenceFactory.class);
	
    @Override
    public Subsequence createSubsequence(String sourceInitial, String targetFinal, int numberSubsequence) {
    	logger.info("Factory");
        return new Subsequence(sourceInitial, targetFinal, numberSubsequence);
    }
}