package ru.sample.submanager.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank @Email String email,
                                @NotBlank String fullName) {}
