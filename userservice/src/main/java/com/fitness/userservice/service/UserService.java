package com.fitness.userservice.service;

import com.fitness.userservice.converter.UserConverter;
import com.fitness.userservice.dto.request.UserRequest;
import com.fitness.userservice.dto.response.UserResponse;
import com.fitness.userservice.exception.UserAlreadyExistsException;
import com.fitness.userservice.exception.UserNotFoundException;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(UserRequest request) {

        if(userRepository.existsByEmail(request.getEmail()))
        {
<<<<<<< HEAD
//            throw new UserAlreadyExistsException("Email already exists");
=======
>>>>>>> 65e5e6f04cb6da8729f16b8c153f642faadfb8c3
            User existingUser = userRepository.findByEmail(request.getEmail());
            return UserConverter.UserToUserResponse(existingUser);
        }

        User user = UserConverter.UserRequestToUser(request);

        User savedUser = userRepository.save(user);
        return UserConverter.UserToUserResponse(savedUser);
    }

    public UserResponse getUserProfile(String userId) {

//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent())
        {
            throw new UserNotFoundException("User not found");
        }

        return UserConverter.UserToUserResponse(userOptional.get());
    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Service for {}", userId);
        return userRepository.existsByKeycloakId(userId);
    }
}
