package com.algorithms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithms.dto.SubsequenceRequest;
import com.algorithms.dto.SubsequenceResponse;
import com.algorithms.model.Subsequence;
import com.algorithms.service.DistinctSubsequencesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/algorithms")
@Tag(name = "DistinctSubsequences", description = "API para operaciones con subsecuencias")
public class DistinctSubsequencesController {

	@Autowired
	private DistinctSubsequencesService subsequenceService;

	@PostMapping("/distinct-subsequences")
	@Operation(summary = "Calcular subsecuencias distintas", description = "Calcula el número de subsecuencias distintas entre la fuente y el objetivo")
	@ApiResponse(responseCode = "201", description = "Subsecuencia creada exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
	@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
	public SubsequenceResponse calculateDistinctSubsequences(@RequestBody SubsequenceRequest request) {
	    int result = subsequenceService.calculateDistinctSubsequences(request.getSource(), request.getTarget());

	    Subsequence subsequence = new Subsequence(request.getSource(), request.getTarget(), result);
	    SubsequenceResponse savedSubsequence = subsequenceService.saveSubsequence(subsequence);

	    return new SubsequenceResponse(savedSubsequence.getId(), savedSubsequence.getSourceInitial(),
	            savedSubsequence.getTargetFinal(), savedSubsequence.getNumberSubsequence(),
	            savedSubsequence.getDateCreate());
	}


	@GetMapping
	@Operation(summary = "Obtener todas las subsecuencias", description = "Retorna una lista de todas las subsecuencias")
	@ApiResponse(responseCode = "200", description = "Lista de subsecuencias obtenida exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
	@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
	public List<SubsequenceResponse> getAllSubsequences() {
	    return subsequenceService.getAllSubsequences();
	}


	@GetMapping("/{id}")
	@Operation(summary = "Obtener subsecuencia por ID", description = "Retorna una subsecuencia específica por su ID")
	@ApiResponse(responseCode = "200", description = "Subsecuencia obtenida exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
	@ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content)
	public ResponseEntity<SubsequenceResponse> getSubsequenceById(@PathVariable Long id) {
	    Optional<SubsequenceResponse> subsequence = subsequenceService.getSubsequenceById(id);
	    return subsequence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}


	@PostMapping
	@Operation(summary = "Crear subsecuencia", description = "Crea una nueva subsecuencia")
	@ApiResponse(responseCode = "201", description = "Subsecuencia creada exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
	@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
	public ResponseEntity<SubsequenceResponse> createSubsequence(@RequestBody Subsequence subsequence) {
	    SubsequenceResponse createdSubsequence = subsequenceService.saveSubsequence(subsequence);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdSubsequence);
	}


	@PutMapping("/{id}")
	@Operation(summary = "Actualizar subsecuencia", description = "Actualiza una subsecuencia existente por su ID")
	@ApiResponse(responseCode = "200", description = "Subsecuencia actualizada exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
	@ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content)
	@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
	public ResponseEntity<SubsequenceResponse> updateSubsequence(@PathVariable Long id, @RequestBody Subsequence subsequence) {
	    SubsequenceResponse updatedSubsequence = subsequenceService.updateSubsequence(id, subsequence);
	    return ResponseEntity.ok(updatedSubsequence);
	}


	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar subsecuencia", description = "Elimina una subsecuencia por su ID")
	@ApiResponse(responseCode = "204", description = "Subsecuencia eliminada exitosamente")
	@ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content)
	public ResponseEntity<Void> deleteSubsequence(@PathVariable Long id) {
	    subsequenceService.deleteSubsequence(id);
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	
}
