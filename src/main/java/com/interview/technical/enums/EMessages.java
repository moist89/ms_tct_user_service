package com.interview.technical.enums;

public enum EMessages {
    MSG_START_METHOD("Iniciando el metodo : {}"),
    MSG_END_METHOD("finalizando el metodo  : {}"),
    MSG_ARGUMENTS("datos de entrada  {}"),
    MSG_GENERAL_ERROR("Ha ocurrido un error al procesar la solicitud"),
    MSG_RESPONSE("datos de salida  {}"),
    MSG_ERROR_USER_NOT_FOUND("No se ha encontrado el usuario"),
    MSG_ERROR_EMAIL_REPEATED("El email ya se encuentra registrado para otro usuario"),
    MSG_ERROR_INVALID_DATA("Datos inválidos en la solicitud ."),
    MSG_ERROR_DATA_INTEGRITY("Error de integridad de datos al procesar el  usuario."),
    MSG_ERROR_EMAIL_NOT_ALLOWS_TO_MODIFY("El email no puede ser modificado"),
    MSG_ERROR_NAME_REPEATED("Ya existe un usuario con el nombre ingresado"),
    MSG_ERROR_PASSWORD_BAD_FORMAT("El password no tiene el formato correcto"),
    SWAGGER_TITLE("API de Creación de Usuarios"),
    SWAGGER_DESCRIPTION("Documentación de endpoints de registro y gestión de usuarios"),
    SWAGGER_VERSION("Documentación de endpoints de registro y gestión de usuarios");


    private final String value;

    public String getValue() {
        return value;
    }

    EMessages(String value) {
        this.value=value ;
    }
}
