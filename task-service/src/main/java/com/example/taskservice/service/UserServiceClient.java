package com.example.taskservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/auth/check")
    Long getUserIdFromToken(@RequestHeader("Authorization") String token);
}
