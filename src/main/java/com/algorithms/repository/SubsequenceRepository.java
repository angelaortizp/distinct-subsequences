package com.algorithms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algorithms.model.Subsequence;


@Repository
public interface SubsequenceRepository extends JpaRepository<Subsequence, Long> {
   
}
