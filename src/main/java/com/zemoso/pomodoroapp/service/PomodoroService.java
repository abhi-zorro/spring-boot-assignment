package com.zemoso.pomodoroapp.service;



import com.zemoso.pomodoroapp.entity.Pomodoro;

import java.util.List;

public interface PomodoroService {
    public List<Pomodoro> findAll();
    public Pomodoro findById(int pomodoroId);
    public Pomodoro save(Pomodoro pomodoro);
    public void deleteById(int pomodoroId);
    public List<Pomodoro> findByTaskId(int taskId);
    public void deleteByTaskId(int taskId);
}
