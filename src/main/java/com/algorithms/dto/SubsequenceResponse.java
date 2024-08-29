package com.algorithms.dto;

import java.time.LocalDateTime;

public class SubsequenceResponse {

    private Long id;
    private String sourceInitial;
    private String targetFinal;
    private int numberSubsequence;
    private LocalDateTime dateCreate;


    public SubsequenceResponse(Long id, String sourceInitial, String targetFinal, int numberSubsequence, LocalDateTime dateCreate) {
        this.id = id;
        this.sourceInitial = sourceInitial;
        this.targetFinal = targetFinal;
        this.numberSubsequence = numberSubsequence;
        this.dateCreate = dateCreate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceInitial() {
        return sourceInitial;
    }

    public void setSourceInitial(String sourceInitial) {
        this.sourceInitial = sourceInitial;
    }

    public String getTargetFinal() {
        return targetFinal;
    }

    public void setTargetFinal(String targetFinal) {
        this.targetFinal = targetFinal;
    }

    public int getNumberSubsequence() {
        return numberSubsequence;
    }

    public void setNumberSubsequence(int numberSubsequence) {
        this.numberSubsequence = numberSubsequence;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }
}
