package com.example.taskservice.model.dto;

import java.util.List;

public record TaskReqDto(String title, String description, List<Long> tagIds) {
}
