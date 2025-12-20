package com.fitness.userservice.repository;

import com.fitness.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);

<<<<<<< HEAD
    Boolean existsByKeycloakId(String userId);
=======
    Boolean existsByKeycloakId(String keycloakId);
>>>>>>> 65e5e6f04cb6da8729f16b8c153f642faadfb8c3

    User findByEmail(String email);
}
