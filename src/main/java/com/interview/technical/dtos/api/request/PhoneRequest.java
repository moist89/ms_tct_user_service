package com.interview.technical.dtos.api.request;
import jakarta.validation.constraints.*;
public record PhoneRequest(
        @NotEmpty(message =" El campo 'number' debe ser informado")
        @Pattern(regexp =  "\\d+", message = "El campo 'number' solo debe contener numeros.")
        String number ,
        @NotEmpty(message =" El campo 'cityCode' debe ser informado")
        @Pattern(regexp = "\\d+", message = "El campo 'cityCode' solo debe contener numeros.")
        String cityCode ,
        @NotEmpty(message =" El campo 'countryCode' debe ser informado")
        @Pattern(regexp = "\\d+", message = "El campo 'countryCode' solo debe contener numeros.")
        String countryCode) {
}
