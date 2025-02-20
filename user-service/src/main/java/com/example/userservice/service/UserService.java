package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.model.dto.JwtAuthResponse;
import com.example.userservice.model.dto.LoginReqDto;
import com.example.userservice.model.dto.UserDto;
import com.example.userservice.model.dto.UserSignupReqDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserDto signup(UserSignupReqDto dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already in use");
        }
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use");
        }

        User user = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .email(dto.email())
                .build();

        userRepository.save(user);

        return new UserDto(user);
    }

    public JwtAuthResponse login(LoginReqDto dto) {

        User user = userRepository.findByUsername(dto.username()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is incorrect"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is incorrect");
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = this.jwtProvider.generateToken(authentication);

        return new JwtAuthResponse(accessToken);
    }
}
