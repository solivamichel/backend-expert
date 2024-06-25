package br.com.soliva.userserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "UserController", description = "Controlador responsável por gerenciar os usuários.")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class)
            ))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "ID do usuário", required = true, example = "667acb2adb540514b3cf9921")
            @PathVariable(name = "id") final String id);
}
