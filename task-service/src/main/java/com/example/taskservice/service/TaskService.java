package com.example.taskservice.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasksByUserId(Long userId) {

        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task, Long userId) {

        task.setUserId(userId);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task, Long userId) {

        Task existingTask = taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id, Long userId) {

        Task task = taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }
}
