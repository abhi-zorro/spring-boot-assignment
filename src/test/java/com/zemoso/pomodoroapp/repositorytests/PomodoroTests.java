package com.zemoso.pomodoroapp.repositorytests;


import com.zemoso.pomodoroapp.dao.PomodoroRepository;
import com.zemoso.pomodoroapp.dao.TaskRepository;
import com.zemoso.pomodoroapp.dao.UserRepository;
import com.zemoso.pomodoroapp.entity.Pomodoro;
import com.zemoso.pomodoroapp.entity.Task;
import com.zemoso.pomodoroapp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PomodoroTests {

    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;
    private Pomodoro pomodoro;

    private Task task;

    private User user;


    @BeforeEach
    void setup(){
        user = user.builder().
                firstName("Benji")
                .lastName("Doe")
                .email("doeB@zemoso.com")
                .password("test")
                .build();
        task = new Task();
        task.setStatus(false);
        task.setUser(user);
        task.setTitle("New Test task");
        task.setEstPomodoros(1);
        pomodoro = new Pomodoro();
        pomodoro.setStartTime(Time.valueOf(LocalTime.now()));
        pomodoro.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(25L)));
        pomodoro.setStatus(0);
        pomodoro.setDate(Date.valueOf(LocalDate.now()));
        pomodoro.setTask(task);

    }

    @DisplayName("JUnit test for save pomodoro operation")
    @Test
    void givenPomodoroObject_whenSave_thenReturnSavedPomodoro(){
        // when - action or the behaviour that we are going test
        Pomodoro pomodoro1 = pomodoroRepository.save(pomodoro);

        // then - verify the output
        assertThat(pomodoro1).isNotNull();
        assertThat(pomodoro1.getId()).isPositive();
    }

    @DisplayName("Junit test for fetch pomodoro")
    @Test
    void givenTaskId_whenFetchPomos(){
        List<Pomodoro> pomos = pomodoroRepository.findByTaskId(task.getId());
        assertThat(pomos).isNotNull();
    }


}
