package com.example.taskservice.controller;

import com.example.taskservice.model.Task;
import com.example.taskservice.service.TaskService;
import com.example.taskservice.service.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserServiceClient userServiceClient;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(HttpServletRequest request) {

        Long userId = getUserIdFromToken(request);

        List<Task> tasks = taskService.getTasksByUserId(userId);

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpServletRequest request) {

        Long userId = getUserIdFromToken(request);

        Task createdTask = taskService.createTask(task, userId);

        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task, HttpServletRequest request) {

        Long userId = getUserIdFromToken(request);

        Task updatedTask = taskService.updateTask(id, task, userId);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, HttpServletRequest request) {

        Long userId = getUserIdFromToken(request);

        taskService.deleteTask(id, userId);

        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        return userServiceClient.getUserIdFromToken(token);
    }
}
