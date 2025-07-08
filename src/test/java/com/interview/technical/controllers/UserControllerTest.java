package com.interview.technical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.technical.dtos.api.response.UserResponse;
import com.interview.technical.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private String requestCreateJson;
    private String responseCreateJson;
    private String responseFindAllJson;
    private String responseFindByIdJson;

    @BeforeEach
    void setUp() throws Exception {
        requestCreateJson = Files.readString(new ClassPathResource("usercontroller_tests/requests/create_valid.txt").getFile().toPath());
        responseCreateJson = Files.readString(new ClassPathResource("usercontroller_tests/responses/create_valid.txt").getFile().toPath());
        responseFindAllJson = Files.readString(new ClassPathResource("usercontroller_tests/responses/findall.txt").getFile().toPath());
        responseFindByIdJson = Files.readString(new ClassPathResource("usercontroller_tests/responses/findbyid.txt").getFile().toPath());
    }

    @Test
    @DisplayName("Debería crear un usuario y retornar 201")
    void createUser_shouldReturnCreated() throws Exception {
        UserResponse mockedResponse = objectMapper.readValue(responseCreateJson, UserResponse.class);
        Mockito.when(userService.create(Mockito.any())).thenReturn(mockedResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestCreateJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseCreateJson));
    }

    @Test
    @DisplayName("Debería retornar 400 cuando el body es nulo")
    void createUser_shouldReturnBadRequestOnNullBody() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Debería retornar 400 cuando campos requeridos están vacíos")
    void createUser_shouldReturnBadRequestOnMissingFields() throws Exception {
        String invalidRequestJson = Files.readString(new ClassPathResource("usercontroller_tests/requests/create_invalid_missing_fields.txt").getFile().toPath());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería listar todos los usuarios")
    void findAll_shouldReturnUserList() throws Exception {
        List<UserResponse> mockedList = List.of(objectMapper.readValue(responseFindAllJson, UserResponse[].class));

        Mockito.when(userService.findAll(null, null)).thenReturn(mockedList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseFindAllJson));
    }

    @Test
    @DisplayName("Debería obtener usuario por ID")
    void findById_shouldReturnUser() throws Exception {
        UserResponse mockedUser = objectMapper.readValue(responseFindByIdJson, UserResponse.class);

        Mockito.when(userService.findById("e83d1590-01d9-4d92-b33a-0d3a431f9f83")).thenReturn(mockedUser);

        mockMvc.perform(get("/users/e83d1590-01d9-4d92-b33a-0d3a431f9f83"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseFindByIdJson));
    }

}
