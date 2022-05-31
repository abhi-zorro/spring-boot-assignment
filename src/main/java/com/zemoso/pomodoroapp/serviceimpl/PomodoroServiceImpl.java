package com.zemoso.pomodoroapp.serviceimpl;

import com.zemoso.pomodoroapp.dao.PomodoroRepository;
import com.zemoso.pomodoroapp.entity.Pomodoro;
import com.zemoso.pomodoroapp.exception.PomodoroNotFoundException;
import com.zemoso.pomodoroapp.service.PomodoroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PomodoroServiceImpl implements PomodoroService {

    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Override
    public List<Pomodoro> findAll() {
        return pomodoroRepository.findAll();
    }

    @Override
    public Pomodoro findById(int pomodoroId) {
        Optional<Pomodoro> result = pomodoroRepository.findById(pomodoroId);
        Pomodoro pomodoro;
        if(result.isPresent()){
            pomodoro = result.get();
            return pomodoro;
        }
        else{
            throw new PomodoroNotFoundException();
        }
    }

    @Override
    public Pomodoro save(Pomodoro pomodoro) {
        return pomodoroRepository.save(pomodoro);
    }

    @Override
    public void deleteById(int pomodoroId) {
        pomodoroRepository.deleteById(pomodoroId);
    }

    @Override
    public List<Pomodoro> findByTaskId(int taskId) {
        return pomodoroRepository.findByTaskId(taskId);
    }

    @Override
    public void deleteByTaskId(int taskId) {
        pomodoroRepository.deleteByTaskId(taskId);
    }
}
