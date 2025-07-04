package com.interview.technical.services;

import com.interview.technical.dtos.api.request.UserRequest;
import com.interview.technical.dtos.api.request.UserUpdateRequest;
import com.interview.technical.dtos.api.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findById(String id);

    List<UserResponse> findAll(String name, String email);

    UserResponse update(UserUpdateRequest userRequest);

    UserResponse create(UserRequest userRequest);

    void delete(String id);
}
