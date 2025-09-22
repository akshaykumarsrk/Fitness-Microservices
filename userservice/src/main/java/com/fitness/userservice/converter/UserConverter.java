package com.fitness.userservice.converter;

import com.fitness.userservice.dto.request.UserRequest;
import com.fitness.userservice.dto.response.UserResponse;
import com.fitness.userservice.model.User;

public class UserConverter {

    public static User UserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public static UserResponse UserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
