package com.algorithms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.model.Subsequence;
import com.algorithms.repository.SubsequenceRepository;

@Service
public class DistinctSubsequencesService {
	
	private static final Logger logger = LoggerFactory.getLogger(DistinctSubsequencesService.class);

	@Autowired
	private SubsequenceRepository subsequenceRepository;

	public SubsequenceResponse saveSubsequence(Subsequence subsequence) {
		Subsequence savedSubsequence = subsequenceRepository.save(subsequence);
		return mapToDTO(savedSubsequence);
	}

	public List<SubsequenceResponse> getAllSubsequences() {
		logger.info("Obteniendo todas las subsecuencias");
		return subsequenceRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public Optional<SubsequenceResponse> getSubsequenceById(Long id) {
		return subsequenceRepository.findById(id).map(this::mapToDTO);
	}

	public void deleteSubsequence(Long id) {
		subsequenceRepository.deleteById(id);
	}

	public SubsequenceResponse updateSubsequence(Long id, Subsequence newSubsequence) {
		Subsequence updatedSubsequence = subsequenceRepository.findById(id).map(subsequence -> {
			subsequence.setSourceInitial(newSubsequence.getSourceInitial());
			subsequence.setTargetFinal(newSubsequence.getTargetFinal());
			subsequence.setNumberSubsequence(newSubsequence.getNumberSubsequence());
			return subsequenceRepository.save(subsequence);
		}).orElseGet(() -> {
			newSubsequence.setId(id);
			return subsequenceRepository.save(newSubsequence);
		});

		return mapToDTO(updatedSubsequence);
	}

	public int calculateDistinctSubsequences(String source, String target) {

		logger.info("Longitud de las cadenas de entrada");
		int sourceLength = source.length();
		int targetLength = target.length();
	
		logger.info("Matriz 2D que guardará las subsecuencias distintas encontradas teniendo en cuenta el caso vacío");
		int[][] subsequencesCount = new int[sourceLength + 1][targetLength + 1];
		
		
		// Se inicializa la matriz llenando la primera columna donde j es igual a 0 ya
		// que cualquier subsecuencia de source siempre se podrá formar
		// la subsecuencia vacía de target siempre de una forma la cual es omitiendo los
		// caracteres de source
		
		logger.info("Se inicializa la matriz llenando la base");
		IntStream.rangeClosed(0, sourceLength).forEach(i -> {
			subsequencesCount[i][0] = 1;
			logger.info("Matriz con escenario base[" + i + "][0]:");
			printMatrix(subsequencesCount, sourceLength, targetLength);
		});

		logger.info("Se recorre la matriz para calcular las subsecuencias");
		IntStream.rangeClosed(1, sourceLength).forEach(i -> IntStream.rangeClosed(1, targetLength).forEach(j -> {

			// Se compara si los caracteres actuales coinciden
			if (source.charAt(i - 1) == target.charAt(j - 1)) {
				// Si coinciden se suman las subsecuencias formadas sin el carácter actual y con
				// el carácter actual
				subsequencesCount[i][j] = subsequencesCount[i - 1][j - 1] + subsequencesCount[i - 1][j];
			} else {
				// Si no coinciden se toma el valor de la fila anterior
				subsequencesCount[i][j] = subsequencesCount[i - 1][j];
			}

			// Imprimir la matriz después de actualizar cada celda
			logger.info("Matriz después de actualizar subsequencesCount[" + i + "][" + j + "]:");
			printMatrix(subsequencesCount, sourceLength, targetLength);
		}));

		// Retornamos el número total de subsecuencias distintas
		logger.info("Subsecuencias encontradas: " + subsequencesCount[sourceLength][targetLength]);

		return subsequencesCount[sourceLength][targetLength];
	}

	private SubsequenceResponse mapToDTO(Subsequence subsequence) {
		return new SubsequenceResponse(subsequence.getId(), subsequence.getSourceInitial(),
				subsequence.getTargetFinal(), subsequence.getNumberSubsequence(), subsequence.getDateCreate());
	}

	private void printMatrix(int[][] matrix, int rows, int cols) {
		IntStream.rangeClosed(0, rows).forEach(i -> {
			IntStream.rangeClosed(0, cols).forEach(j -> System.out.print(matrix[i][j] + " "));
			logger.info("------------------------------------------------------------------");
		});
		logger.info("**********************************************************************");
	}

}
