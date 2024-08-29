package com.algorithms.dto;

public class SubsequenceRequest {
    private String source;
    private String target;
    private int numberSubsequence;


    public SubsequenceRequest() {
    }

    public SubsequenceRequest(String source, String target, int numberSubsequence) {
        this.source = source;
        this.target = target;
        this.numberSubsequence = numberSubsequence;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getNumberSubsequence() {
        return numberSubsequence;
    }

    public void setNumberSubsequence(int numberSubsequence) {
        this.numberSubsequence = numberSubsequence;
    }
    
}