package com.example.taskservice.service;


import com.example.taskservice.model.dto.TagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tag-service")
public interface TagServiceClient {

    @GetMapping("/tags/{tagId}")
    TagResponse getTagById(@PathVariable Long tagId);
}
