package com.algorithms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.algorithms.util.exception.ErrorResponse;
import com.algorithms.util.exception.InvalidInputException;
import com.algorithms.util.exception.SubsequenceProcessingException;

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
	@ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "409", description = "Subsecuencia igual ya existe", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<?> calculateDistinctSubsequences(@RequestBody SubsequenceRequest request) {
	    try {
	        int result = subsequenceService.calculateDistinctSubsequences(request.getSource(), request.getTarget());
	        
	        if (subsequenceService.existsSimilarSubsequence(request.getSource(), request.getTarget(), result)) {
	            ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.CONFLICT.value(),
	                "Ya existe una subsecuencia igual en la base de datos."
	            );
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	        }
	        
	        SubsequenceResponse savedSubsequence = subsequenceService.saveSubsequence(
	            request.getSource(), request.getTarget(), result
	        );
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubsequence);
	    } catch (InvalidInputException e) {
	        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (SubsequenceProcessingException e) {
	        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

    @GetMapping
    @Operation(summary = "Obtener todas las subsecuencias", description = "Retorna una lista de todas las subsecuencias")
    @ApiResponse(responseCode = "200", description = "Lista de subsecuencias obtenida exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
    public ResponseEntity<List<SubsequenceResponse>> getAllSubsequences() {
        List<SubsequenceResponse> subsequences = subsequenceService.getAllSubsequences();
        return ResponseEntity.ok(subsequences);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener subsecuencia por ID", description = "Retorna una subsecuencia específica por su ID")
    @ApiResponse(responseCode = "200", description = "Subsecuencia obtenida exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
    @ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content)
    public ResponseEntity<SubsequenceResponse> getSubsequenceById(@PathVariable Long id) {
        SubsequenceResponse subsequence = subsequenceService.getSubsequenceById(id);
        return ResponseEntity.ok(subsequence);
    }

    @PostMapping
    @Operation(summary = "Crear subsecuencia", description = "Crea una nueva subsecuencia")
    @ApiResponse(responseCode = "201", description = "Subsecuencia creada exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Subsecuencia igual ya existe", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<?> createSubsequence(@RequestBody SubsequenceRequest request) {
        try {
            int calculatedResult = subsequenceService.calculateDistinctSubsequences(request.getSource(), request.getTarget());

            if (calculatedResult != request.getNumberSubsequence()) {
                ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "El número de subsecuencias proporcionado no coincide con el cálculo real. Calculado: " + calculatedResult + ", Proporcionado: " + request.getNumberSubsequence()
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            if (subsequenceService.existsSimilarSubsequence(request.getSource(), request.getTarget(), request.getNumberSubsequence())) {
                ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    "Ya existe una subsecuencia igual en la base de datos."
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            }

            SubsequenceResponse createdSubsequence = subsequenceService.saveSubsequence(
                request.getSource(), request.getTarget(), request.getNumberSubsequence()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubsequence);
        } catch (InvalidInputException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (SubsequenceProcessingException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar subsecuencia", description = "Actualiza una subsecuencia existente por su ID")
    @ApiResponse(responseCode = "200", description = "Subsecuencia actualizada exitosamente", content = @Content(schema = @Schema(implementation = SubsequenceResponse.class)))
    @ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Subsecuencia igual ya existe", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<?> updateSubsequence(@PathVariable Long id, @RequestBody SubsequenceRequest request) {
        int calculatedResult = subsequenceService.calculateDistinctSubsequences(request.getSource(), request.getTarget());
        
        if (calculatedResult != request.getNumberSubsequence()) {
            ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "El número de subsecuencias proporcionado no coincide con el cálculo real. Calculado: " + calculatedResult + ", Proporcionado: " + request.getNumberSubsequence()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        if (subsequenceService.existsSimilarSubsequence(request.getSource(), request.getTarget(), request.getNumberSubsequence())) {
            ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Ya existe una subsecuencia igual en la base de datos."
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        
        Subsequence subsequence = new Subsequence(request.getSource(), request.getTarget(), request.getNumberSubsequence());
        subsequence.setId(id);
        SubsequenceResponse updatedSubsequence = subsequenceService.updateSubsequence(id, subsequence);
        return ResponseEntity.ok(updatedSubsequence);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar subsecuencia", description = "Elimina una subsecuencia por su ID")
    @ApiResponse(responseCode = "200", description = "Subsecuencia eliminada exitosamente")
    @ApiResponse(responseCode = "404", description = "Subsecuencia no encontrada", content = @Content)
    public ResponseEntity<Map<String, String>> deleteSubsequence(@PathVariable Long id) {
        subsequenceService.deleteSubsequence(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subsecuencia con ID " + id + " ha sido eliminada exitosamente");
        return ResponseEntity.ok(response);
    }
	
}
