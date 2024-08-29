package com.algorithms.strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.IntStream;

public class DynamicProgrammingStrategy implements SubsequenceCalculationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DynamicProgrammingStrategy.class);

    @Override
    public int calculateDistinctSubsequences(String source, String target) {
        logger.info("Calculating distinct subsequences using Dynamic Programming strategy");

        int sourceLength = source.length();
        int targetLength = target.length();

        int[][] subsequencesCount = new int[sourceLength + 1][targetLength + 1];

        IntStream.rangeClosed(0, sourceLength).forEach(i -> {
            subsequencesCount[i][0] = 1;
            logger.info("Matrix with base scenario [" + i + "][0]:");
            printMatrix(subsequencesCount, sourceLength, targetLength);
        });

        IntStream.rangeClosed(1, sourceLength).forEach(i -> 
            IntStream.rangeClosed(1, targetLength).forEach(j -> {
                if (source.charAt(i - 1) == target.charAt(j - 1)) {
                    subsequencesCount[i][j] = subsequencesCount[i - 1][j - 1] + subsequencesCount[i - 1][j];
                } else {
                    subsequencesCount[i][j] = subsequencesCount[i - 1][j];
                }

                logger.info("Matrix after updating subsequencesCount[" + i + "][" + j + "]:");
                printMatrix(subsequencesCount, sourceLength, targetLength);
            })
        );

        logger.info("Subsequences found: " + subsequencesCount[sourceLength][targetLength]);
        return subsequencesCount[sourceLength][targetLength];
    }

    private void printMatrix(int[][] matrix, int rows, int cols) {
        IntStream.rangeClosed(0, rows).forEach(i -> {
            IntStream.rangeClosed(0, cols).forEach(j -> System.out.print(matrix[i][j] + " "));
            logger.info("------------------------------------------------------------------");
        });
        logger.info("**********************************************************************");
    }
}

