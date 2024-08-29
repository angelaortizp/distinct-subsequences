package com.algorithms.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.factory.StandardSubsequenceFactory;
import com.algorithms.factory.SubsequenceFactory;
import com.algorithms.model.Subsequence;
import com.algorithms.repository.SubsequenceRepository;
import com.algorithms.strategy.DynamicProgrammingStrategy;
import com.algorithms.strategy.SubsequenceCalculationStrategy;
import com.algorithms.util.exception.InvalidInputException;
import com.algorithms.util.exception.SubsequenceNotFoundException;
import com.algorithms.util.exception.SubsequenceProcessingException;

@Service
public class DistinctSubsequencesService {

	private static final Logger logger = LoggerFactory.getLogger(DistinctSubsequencesService.class);

	@Autowired
	private SubsequenceRepository subsequenceRepository;

	private static DistinctSubsequencesService instance;

	private SubsequenceFactory factory = new StandardSubsequenceFactory();

	private SubsequenceCalculationStrategy calculationStrategy = new DynamicProgrammingStrategy();

	public void setCalculationStrategy(SubsequenceCalculationStrategy strategy) {
		this.calculationStrategy = strategy;
	}

	private DistinctSubsequencesService() {
	}

	public static synchronized DistinctSubsequencesService getInstance() {
		logger.info("Unica instancia");
		if (instance == null) {
			instance = new DistinctSubsequencesService();
		}
		return instance;
	}

	public SubsequenceResponse saveSubsequence(String sourceInitial, String targetFinal, int numberSubsequence) {
		logger.info("Guardar Subsecuencia");
		try {
			Subsequence subsequence = factory.createSubsequence(sourceInitial, targetFinal, numberSubsequence);
			Subsequence savedSubsequence = subsequenceRepository.save(subsequence);
			logger.info("Subsecuencia guardada exitosamente");
			return mapToDTO(savedSubsequence);
		} catch (Exception e) {
			logger.error("Error al guardar la subsecuencia", e);
			throw new SubsequenceProcessingException("Error al guardar la subsecuencia: " + e.getMessage());
		}
	}

	public List<SubsequenceResponse> getAllSubsequences() {
		logger.info("Obteniendo todas las subsecuencias");
		try {
			return subsequenceRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error al obtener todas las subsecuencias", e);
			throw new SubsequenceProcessingException("Error al obtener todas las subsecuencias: " + e.getMessage());
		}
	}

	public SubsequenceResponse getSubsequenceById(Long id) {
		logger.info("Obtener Subsecuencia por Id");
		return subsequenceRepository.findById(id).map(this::mapToDTO)
				.orElseThrow(() -> new SubsequenceNotFoundException("Subsecuencia no encontrada con id: " + id));
	}

	public void deleteSubsequence(Long id) {
		logger.info("Eliminar Subsecuencia por Id");
		try {
			if (!subsequenceRepository.existsById(id)) {
				throw new SubsequenceNotFoundException("Subsecuencia no encontrada con id: " + id);
			}
			subsequenceRepository.deleteById(id);
		} catch (SubsequenceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error al eliminar la subsecuencia", e);
			throw new SubsequenceProcessingException("Error al eliminar la subsecuencia: " + e.getMessage());
		}
	}

	public SubsequenceResponse updateSubsequence(Long id, Subsequence newSubsequence) {
		logger.info("Actualizar subsecuencia");
		try {
			Subsequence updatedSubsequence = subsequenceRepository.findById(id).map(subsequence -> {
				subsequence.setSourceInitial(newSubsequence.getSourceInitial());
				subsequence.setTargetFinal(newSubsequence.getTargetFinal());
				subsequence.setNumberSubsequence(newSubsequence.getNumberSubsequence());
				return subsequenceRepository.save(subsequence);
			}).orElseThrow(() -> new SubsequenceNotFoundException("Subsecuencia no encontrada con id: " + id));

			return mapToDTO(updatedSubsequence);
		} catch (SubsequenceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error al actualizar la subsecuencia", e);
			throw new SubsequenceProcessingException("Error al actualizar la subsecuencia: " + e.getMessage());
		}
	}

	public int calculateDistinctSubsequences(String source, String target) {
		logger.info("Validación restricción de longitud");
		if (source.length() < 1 || source.length() > 1000 || target.length() < 1 || target.length() > 1000) {
			throw new InvalidInputException("La longitud de los textos deben estar entre 1 y 1000 caracteres.");
		}

		logger.info("Validación alfabeto en inglés");
		if (!source.matches("[a-zA-Z]+") || !target.matches("[a-zA-Z]+")) {
			throw new InvalidInputException("Los textos deben contener solo letras del alfabeto inglés.");
		}

		return calculationStrategy.calculateDistinctSubsequences(source, target);
	}

	public boolean existsSimilarSubsequence(String source, String target, int numberSubsequence) {
		return subsequenceRepository.existsBySourceInitialAndTargetFinalAndNumberSubsequence(source, target,
				numberSubsequence);
	}

	public SubsequenceResponse mapToDTO(Subsequence subsequence) {
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
