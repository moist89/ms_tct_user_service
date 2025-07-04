package com.interview.technical.dtos.api.response;

import java.time.LocalDateTime;

public record UserResponse(String id ,
                           String name,
                           String email,
                           LocalDateTime created ,
                           LocalDateTime modified,
                           LocalDateTime lastLogin,
                           String token,
                           boolean isActive) {
}
