package com.interview.technical.handlers;

import com.interview.technical.exceptions.EmailAlreadyExistsException;
import com.interview.technical.exceptions.GeneralException;
import com.interview.technical.exceptions.InvalidPasswordException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionHandler { @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    String mensaje = ex.getBindingResult().getFieldErrors()
            .stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("Error de validación");

    return errorResponse(mensaje, HttpStatus.BAD_REQUEST);
}

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
        return errorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, GeneralException.class})
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return errorResponse("Error interno: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordException(InvalidPasswordException ex) {
        Map<String, String> error = Map.of("mensaje", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Map<String, String>> errorResponse(String mensaje, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", mensaje);
        return new ResponseEntity<>(error, status);
    }
}
