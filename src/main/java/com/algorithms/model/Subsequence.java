package com.algorithms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subsequences")
public class Subsequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_initial", nullable = false)
    private String sourceInitial;

    @Column(name = "target_final", nullable = false)
    private String targetFinal;

    @Column(name = "number_subsequence", nullable = false)
    private int numberSubsequence;

    @Column(name = "date_create", nullable = false, updatable = false)
    private LocalDateTime dateCreate;

   
    public Subsequence() {}

   
    public Subsequence(String sourceInitial, String targetFinal, int numberSubsequence) {
        this.sourceInitial = sourceInitial;
        this.targetFinal = targetFinal;
        this.numberSubsequence = numberSubsequence;
        this.dateCreate = LocalDateTime.now(); 
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
