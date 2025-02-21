package com.example.taskservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == HttpStatus.FORBIDDEN.value()) {
            return new ResponseStatusException(HttpStatus.FORBIDDEN, response.reason());
        }

        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, response.reason());
        }

        if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED, response.reason());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
