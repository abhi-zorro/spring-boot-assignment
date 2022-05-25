package com.zemoso.pomodoroapp.service;

import com.zemoso.pomodoroapp.entity.Task;

import java.util.List;

public interface TaskService {
    public List<Task> findAll();
    public Task findById(int taskId);
    public Task save(Task task);
    public void deleteById(int taskId);
    public List<Task> findByUserId(int userId);
    public void deleteByUserId(int userId);
    public void deleteByTaskId(int taskId);
}
