package com.interview.technical.mappers;

import com.interview.technical.dtos.api.request.UserRequest;
import com.interview.technical.dtos.api.request.UserUpdateRequest;
import com.interview.technical.dtos.api.response.UserResponse;
import com.interview.technical.enums.EMessages;
import com.interview.technical.models.Phone;
import com.interview.technical.models.User;
import com.interview.technical.utils.JWTUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserMapper {
    public static UserResponse map(User user) {
        if (user == null) {
            throw new IllegalArgumentException(EMessages.MSG_ERROR_USER_NOT_FOUND.getValue());
        }
        return new UserResponse(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive()
        );
    }

    public static User map(UserRequest userRequest) {

        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setToken(JWTUtils.generateToken(user.getName(),user.getEmail()));
        user.setPassword(userRequest.password());


        return user;
    }

}
