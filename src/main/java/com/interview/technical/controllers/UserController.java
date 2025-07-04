package com.interview.technical.controllers;

import com.interview.technical.dtos.api.request.UserRequest;
import com.interview.technical.dtos.api.request.UserUpdateRequest;
import com.interview.technical.dtos.api.response.UserResponse;
import com.interview.technical.enums.EMessages;
import com.interview.technical.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email duplicado",
                    content = @Content(schema = @Schema(example = "{\"mensaje\": \"mensaje de error\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(example = "{\"mensaje\": \"mensaje de error\"}")))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest userRequest) throws URISyntaxException {
        logger.info(EMessages.MSG_START_METHOD.getValue(), "create");

        UserResponse userResponse = userService.create(userRequest);

        logger.info(EMessages.MSG_END_METHOD.getValue(), "create");

        return ResponseEntity.created(new URI("/users/" + userResponse.id()))
                .body(userResponse);
    }

    @Operation(summary = "Actualizar un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(example = "{\"mensaje\": \"mensaje de error\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"mensaje\": \"mensaje de error\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest userRequest) {

        logger.info(EMessages.MSG_START_METHOD.getValue(), "update");

        UserResponse response = userService.update(userRequest);

        logger.info(EMessages.MSG_END_METHOD.getValue(), "update");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar usuarios (con filtros opcionales)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> findAll(
            @Parameter(description = "Filtrar por nombre") @RequestParam(required = false) String name,
            @Parameter(description = "Filtrar por email") @RequestParam(required = false) String email) {
        logger.info(EMessages.MSG_START_METHOD.getValue(), "findAll");

        List<UserResponse> response = userService.findAll(name, email);

        logger.info(EMessages.MSG_END_METHOD.getValue(), "findAll");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"mensaje\": \"mensaje de error\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserResponse> findById(
            @Parameter(description = "ID del usuario") @PathVariable String id) {
        logger.info(EMessages.MSG_START_METHOD.getValue(), "findById");

        UserResponse response = userService.findById(id);

        logger.info(EMessages.MSG_END_METHOD.getValue(), "findById");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable String id) {
        logger.info(EMessages.MSG_START_METHOD.getValue(), "delete");
        userService.delete(id);
        logger.info(EMessages.MSG_END_METHOD.getValue(), "delete");

        return ResponseEntity.noContent().build();
    }
}
