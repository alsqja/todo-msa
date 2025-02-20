package com.example.userservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignupReqDto(@NotBlank String username, @NotBlank String password, @Email String email) {

}
