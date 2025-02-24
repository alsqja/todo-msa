package com.example.taskservice.model.dto;

import java.util.List;

public record TaskResDto(Long id, String title, String description, Long userId, List<TagResponse> tags) {
}
