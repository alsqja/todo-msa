package com.example.taskservice.service;

import com.example.taskservice.model.Task;
import com.example.taskservice.model.TaskTag;
import com.example.taskservice.model.dto.TagResponse;
import com.example.taskservice.model.dto.TaskReqDto;
import com.example.taskservice.model.dto.TaskResDto;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TagServiceClient tagServiceClient;
    private final TaskTagRepository taskTagRepository;

    public List<TaskResDto> getTasksByUserId(Long userId) {

        List<Task> tasks = taskRepository.findByUserId(userId);
        List<TaskResDto> tagResponses = new ArrayList<>();

        for (Task task : tasks) {

            List<TagResponse> tags = new ArrayList<>();

            for (TaskTag taskTag : task.getTags()) {
                TagResponse tagResponse = tagServiceClient.getTagById(taskTag.getTagId());
                tags.add(tagResponse);
            }

            tagResponses.add(new TaskResDto(task.getId(), task.getTitle(), task.getDescription(), task.getUserId(), tags));
        }

        return tagResponses;
    }

    public TaskResDto createTask(TaskReqDto dto, Long userId) {

        Task task = new Task(dto.title(), dto.description(), userId);

        Task savedTask = taskRepository.save(task);

        List<TagResponse> tags = new ArrayList<>();

        for (Long tagId : dto.tagIds()) {
            TaskTag taskTag = new TaskTag(savedTask, tagId);
            taskTagRepository.save(taskTag);
            tags.add(tagServiceClient.getTagById(taskTag.getTagId()));
        }

        return new TaskResDto(task.getId(), task.getTitle(), task.getDescription(), task.getUserId(), tags);
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
