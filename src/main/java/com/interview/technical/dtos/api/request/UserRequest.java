package com.interview.technical.dtos.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


import java.util.List;

public record UserRequest(
        @NotEmpty(message =" El campo 'name' debe ser informado")
        String name ,
        @NotEmpty(message =" El campo 'email' debe ser informado")
        @Email(message = "El campo 'email' no tiene formato de Email")
        String email,
        @NotEmpty(message =" El campo 'password' debe ser informado")
        String password,
        @Valid
        List<PhoneRequest>phones) {
}
