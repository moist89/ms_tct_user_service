package com.interview.technical.services;

import com.interview.technical.dtos.api.request.UserRequest;
import com.interview.technical.dtos.api.response.UserResponse;
import com.interview.technical.enums.EMessages;
import com.interview.technical.exceptions.GeneralException;
import com.interview.technical.mappers.UserMapper;
import com.interview.technical.models.User;
import com.interview.technical.repositories.UserJpaRepository;
import com.interview.technical.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponse userResponse;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john@example.com");

        userResponse = new UserResponse("1", null, null, null,"token",false);

        userRequest = new UserRequest("John Doe", "john@example.com", "password123", List.of());
    }

    @Test
    @DisplayName("Debe retornar usuario si el ID existe")
    void findById_ReturnsUser_WhenIdExists() {
        when(userJpaRepository.findById("1")).thenReturn(Optional.of(user));

        UserResponse result = userService.findById("1");

        assertNotNull(result);
        assertEquals("1", result.id());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el ID no existe")
    void findById_ThrowsException_WhenIdNotFound() {
        when(userJpaRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> userService.findById("1"));
    }

    @Test
    @DisplayName("Debe retornar todos los usuarios cuando no se pasa filtro")
    void findAll_ReturnsAllUsers_WhenNoFilter() {
        when(userJpaRepository.findAll()).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll("", "");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar usuarios por nombre")
    void findAll_ReturnsUsers_WhenFilteringByName() {
        when(userJpaRepository.findByName("John Doe")).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll("John Doe", "");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar usuarios por email")
    void findAll_ReturnsUsers_WhenFilteringByEmail() {
        when(userJpaRepository.findByEmail("john@example.com")).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll("", "john@example.com");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar usuarios por nombre y email")
    void findAll_ReturnsUsers_WhenFilteringByNameAndEmail() {
        when(userJpaRepository.findByNameAndEmail("John Doe", "john@example.com")).thenReturn(List.of(user));

        List<UserResponse> result = userService.findAll("John Doe", "john@example.com");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando hay error de integridad de datos")
    void findAll_ThrowsGeneralException_WhenDataIntegrityViolation() {
        when(userJpaRepository.findAll()).thenThrow(new org.springframework.dao.DataIntegrityViolationException(""));

        assertThrows(GeneralException.class, () -> userService.findAll("", ""));
    }

    @Test
    @DisplayName("Debe crear usuario correctamente")
    void create_CreatesUserSuccessfully() {
        when(userJpaRepository.save(any(User.class))).thenReturn(user);

        UserResponse result = userService.create(userRequest);

        assertNotNull(result);
        assertEquals("1", result.id());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando hay error en persistencia")
    void create_ThrowsException_WhenRepositoryFails() {
        when(userJpaRepository.save(any(User.class)))
                .thenThrow(new org.springframework.dao.DataAccessException(EMessages.MSG_GENERAL_ERROR.getValue()){});

        assertThrows(GeneralException.class, () -> userService.create(userRequest));
    }
}
