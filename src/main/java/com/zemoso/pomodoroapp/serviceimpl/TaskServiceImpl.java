package com.zemoso.pomodoroapp.serviceimpl;

import com.zemoso.pomodoroapp.dao.TaskRepository;
import com.zemoso.pomodoroapp.entity.Task;
import com.zemoso.pomodoroapp.exception.TaskNotFoundException;
import com.zemoso.pomodoroapp.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(int taskId) {
        Optional<Task> result = taskRepository.findById(taskId);
        Task task;
        if(result.isPresent()){
            task = result.get();
            return task;
        }
        else{
            throw new TaskNotFoundException();
        }
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(int taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> findByUserId(int userId) {
        List<Task> taskList = taskRepository.findByUserId(userId);
        if(taskList.isEmpty()){
            throw new TaskNotFoundException();
        }
        else{
            return taskList;
        }
    }

    @Override
    public void deleteByUserId(int userId) {
        log.info(">>>>> INSIDE TASK SERVICE : deleteByUserId method");
        taskRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteByTaskId(int taskId) {
        taskRepository.deleteByTaskId(taskId);
    }
}
