package com.zemoso.pomodoroapp.repositorytests;

import com.zemoso.pomodoroapp.dao.TaskRepository;
import com.zemoso.pomodoroapp.dao.UserRepository;
import com.zemoso.pomodoroapp.entity.Task;
import com.zemoso.pomodoroapp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskTests {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    private User user;

    private Task task;

    @BeforeEach
    void setup(){
        task = new Task();
        task.setTitle("new task");
        task.setStatus(false);
        task.setEstPomodoros(1);
        task.setNote("Some task title");
        user = user.builder().
                firstName("Benjamn")
                .lastName("Doe")
                .email("benja@zemosolabs.com")
                .password("test")
                .build();
        task.setUser(user);
    }

    @DisplayName("Junit save and create task")
    @Test
    void saveTaskCreate(){
        user = userRepository.save(user);
        System.out.println("Task: " + task.getId());
        assertThat(task).isNotNull();
    }
    @DisplayName("JUnit task list test")
    @Test
    void returnTaskList_givenUserId(){
        List<Task> taskList = taskRepository.findByUserId(task.getUser().getId());
        assertThat(taskList).isNotNull();
    }
}
