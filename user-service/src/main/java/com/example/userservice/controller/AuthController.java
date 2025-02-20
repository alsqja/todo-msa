package com.example.userservice.controller;

import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.dto.UserSignupReqDto;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserSignupReqDto dto) {


        return new ResponseEntity<>(userService.signup(dto), HttpStatus.CREATED);
    }
}
