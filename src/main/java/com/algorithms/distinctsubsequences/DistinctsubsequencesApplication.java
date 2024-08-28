package com.algorithms.distinctsubsequences;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class DistinctsubsequencesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistinctsubsequencesApplication.class, args);
		
		calculateDistinctSubsequences();

		
	}
	
	
	public static void calculateDistinctSubsequences() {
	    
	    String source = "babgbag";
	    String target = "bag";
	    
	    // Longitud de las cadenas de entrada
	    int sourceInitial = source.length();
	    int targetFinal = target.length();
	    
	    // Matriz 2D que guardará las subsecuencias distintas encontradas teniendo en cuenta el caso vacío
	    int[][] subsequencesCount = new int[sourceInitial + 1][targetFinal + 1];
	    
	    
	    // Se inicializa la matriz llenando la primera columna donde j es igual a 0 ya que cualquier subsecuencia de source siempre se podrá formar
	    // la subsecuencia vacía de target siempre de una forma la cual es omitiendo los caracteres de source
	    for (int i = 0; i <= sourceInitial; i++) {
	        subsequencesCount[i][0] = 1;
	        System.out.println("Matriz con escenario base[" + i + "][" + 0 + "]:");
            printMatrix(subsequencesCount, sourceInitial, targetFinal);
	    }
	    
	    for (int i = 1; i <= sourceInitial; i++) {
	        for (int j = 1; j <= targetFinal; j++) {
	            
	            // Se compara si los caracteres actuales coinciden
	            if (source.charAt(i - 1) == target.charAt(j - 1)) {
	                // Si coinciden se suman las subsecuencias formadas sin el carácter actual y con el carácter actual
	                subsequencesCount[i][j] = subsequencesCount[i - 1][j - 1] + subsequencesCount[i - 1][j];
	            } else {
	                // Si no coinciden se toma el valor de la fila anterior
	                subsequencesCount[i][j] = subsequencesCount[i - 1][j];
	            }
	            
	            // Imprimir la matriz después de actualizar cada celda
	            System.out.println("Matriz después de actualizar subsequencesCount[" + i + "][" + j + "]:");
	            printMatrix(subsequencesCount, sourceInitial, targetFinal);
	        }
	    }
	    
	    // Retornamos el número total de subsecuencias distintas
	    System.out.println("Subsecuencias encontradas: " + subsequencesCount[sourceInitial][targetFinal]);
	}
	
	
	private static void printMatrix(int[][] matrix, int rows, int cols) {
	    for (int i = 0; i <= rows; i++) {
	        for (int j = 0; j <= cols; j++) {
	            System.out.print(matrix[i][j] + " ");
	        }
	        System.out.println("---");
	    }
	    System.out.println("******");
	}

}
