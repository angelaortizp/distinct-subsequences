package com.algorithms.factory;

import com.algorithms.model.Subsequence;

public abstract class SubsequenceFactory {
    public abstract Subsequence createSubsequence(String sourceInitial, String targetFinal, int numberSubsequence);
}