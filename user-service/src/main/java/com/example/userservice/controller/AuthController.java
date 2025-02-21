package com.example.userservice.controller;

import com.example.userservice.model.dto.JwtAuthResponse;
import com.example.userservice.model.dto.LoginReqDto;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.dto.UserSignupReqDto;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
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

        return ResponseEntity.ok(userService.signup(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReqDto dto) {

        JwtAuthResponse result = userService.login(dto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws UsernameNotFoundException {

        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            return ResponseEntity.noContent().build();
        }

        throw new UsernameNotFoundException("로그인이 먼저 필요합니다.");
    }

    @GetMapping("/check")
    public ResponseEntity<Long> check(Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(userService.findUserId(username));
    }
}
