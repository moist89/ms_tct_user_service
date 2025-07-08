package com.interview.technical.validators;

import com.interview.technical.enums.EMessages;
import com.interview.technical.exceptions.GeneralException;
import com.interview.technical.exceptions.InvalidPasswordException;
import com.interview.technical.repositories.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserValidatorTest {

    @Mock
    private UserJpaRepository userJpaRepository;


    private UserValidator userValidator;

    private final String validPassword = "Abcd1234";
    private final String invalidPassword = "abc";
    private final String regexPassword = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Pattern regexPattern = Pattern.compile(regexPassword);
        userValidator = new UserValidator(regexPassword, userJpaRepository);
        ReflectionTestUtils.setField(userValidator, "passwordPattern", regexPattern);
    }



    @Test
    @DisplayName("Debe permitir una contraseña válida que cumpla con el patrón configurado")
    void testValidatePassword_Valid() {
        assertDoesNotThrow(() -> userValidator.validatePassword(validPassword));
    }

    @Test
    @DisplayName("Debe lanzar InvalidPasswordException si la contraseña no cumple con el patrón")
    void testValidatePassword_Invalid() {
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () ->
                userValidator.validatePassword(invalidPassword));
        assertEquals(EMessages.MSG_ERROR_PASSWORD_BAD_FORMAT.getValue(), exception.getMessage());
    }

    @Test
    @DisplayName("Debe permitir la creación si el nombre y el email no existen en la base de datos")
    void testVerificarDuplicidad_NoExiste() {
        when(userJpaRepository.existsByNameIgnoreCase("Juan")).thenReturn(false);
        when(userJpaRepository.existsByEmailIgnoreCase("juan@mail.com")).thenReturn(false);

        assertDoesNotThrow(() -> userValidator.verificarDuplicidad("Juan", "juan@mail.com"));
    }

    @Test
    @DisplayName("Debe lanzar GeneralException si el nombre ya está registrado")
    void testVerificarDuplicidad_NombreExiste() {
        when(userJpaRepository.existsByNameIgnoreCase("Juan")).thenReturn(true);
        when(userJpaRepository.existsByEmailIgnoreCase("juan@mail.com")).thenReturn(false);

        GeneralException exception = assertThrows(GeneralException.class, () ->
                userValidator.verificarDuplicidad("Juan", "juan@mail.com"));
        assertEquals(EMessages.MSG_ERROR_NAME_REPEATED.getValue(), exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar GeneralException si el email ya está registrado")
    void testVerificarDuplicidad_EmailExiste() {
        when(userJpaRepository.existsByNameIgnoreCase("Juan")).thenReturn(false);
        when(userJpaRepository.existsByEmailIgnoreCase("juan@mail.com")).thenReturn(true);

        GeneralException exception = assertThrows(GeneralException.class, () ->
                userValidator.verificarDuplicidad("Juan", "juan@mail.com"));
        assertEquals(EMessages.MSG_ERROR_EMAIL_REPEATED.getValue(), exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar GeneralException si tanto el nombre como el email ya están registrados")
    void testVerificarDuplicidad_AmbosExisten() {
        when(userJpaRepository.existsByNameIgnoreCase("Juan")).thenReturn(true);
        when(userJpaRepository.existsByEmailIgnoreCase("juan@mail.com")).thenReturn(true);

        GeneralException exception = assertThrows(GeneralException.class, () ->
                userValidator.verificarDuplicidad("Juan", "juan@mail.com"));
        assertEquals(EMessages.MSG_ERROR_NAME_REPEATED.getValue(), exception.getMessage());
    }
}
