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
                user.getName(),
                user.getEmail(),
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
    public static void map(User user, UserUpdateRequest request) {
        boolean hasChanges = false;

        if (hasNameChanged(user, request)) {
            user.setName(request.name());
            hasChanges = true;
        }

        if (hasEmailChanged(user, request)) {
            user.setEmail(request.email());
            hasChanges = true;
        }

        List<Phone> updatedPhones = mapPhones(request, user);
        if (havePhonesChanged(user.getPhones(), updatedPhones)) {
            user.getPhones().clear();
            user.getPhones().addAll(updatedPhones);
            hasChanges = true;
        }

        if (hasChanges) {
            user.setModified(LocalDateTime.now());
        }
    }

    private static boolean hasNameChanged(User user, UserUpdateRequest request) {
        return !user.getName().equals(request.name());
    }

    private static boolean hasEmailChanged(User user, UserUpdateRequest request) {
        return !user.getEmail().equals(request.email());
    }

    private static List<Phone> mapPhones(UserUpdateRequest request, User user) {
        return new ArrayList<> (
                request.phones().stream()
                        .map(p -> PhoneMapper.map(p, user))
                        .toList()
        );
    }

    private static boolean havePhonesChanged(List<Phone> existingPhones, List<Phone> newPhones) {
        if (existingPhones.size() != newPhones.size()) return true;

        for (int i = 0; i < newPhones.size(); i++) {
            Phone existing = existingPhones.get(i);
            Phone incoming = newPhones.get(i);

            if (!Objects.equals(existing.getNumber(), incoming.getNumber()) ||
                    !Objects.equals(existing.getCityCode(), incoming.getCityCode()) ||
                    !Objects.equals(existing.getCountryCode(), incoming.getCountryCode())) {
                return true;
            }
        }
        return false;
    }
}
