package com.interview.technical.dtos.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserUpdateRequest(
        @NotNull(message = "El campo 'id' es obligatorio")
        String id,

        @NotEmpty(message = "El campo 'name' debe ser informado")
        String name,

        // El email podría omitirse o dejarse inmutable según reglas de negocio
        @NotEmpty(message = "El campo 'email' debe ser informado")
        @Email(message = "El campo 'email' no tiene un formato válido")
        String email,

        @NotEmpty(message = "El campo 'password' debe ser informado")
        String password,

        List<PhoneRequest> phones
) {}
