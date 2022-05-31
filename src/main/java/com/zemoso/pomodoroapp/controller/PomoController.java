package com.zemoso.pomodoroapp.controller;

import com.zemoso.pomodoroapp.dao.PomodoroRepository;
import com.zemoso.pomodoroapp.dao.TaskRepository;
import com.zemoso.pomodoroapp.dao.UserRepository;
import com.zemoso.pomodoroapp.dto.TaskDto;
import com.zemoso.pomodoroapp.entity.Pomodoro;
import com.zemoso.pomodoroapp.entity.Task;
import com.zemoso.pomodoroapp.entity.User;
import com.zemoso.pomodoroapp.exception.TaskNotFoundException;
import com.zemoso.pomodoroapp.mapservice.MapperService;
import com.zemoso.pomodoroapp.service.PomodoroService;
import com.zemoso.pomodoroapp.service.TaskService;
import com.zemoso.pomodoroapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pomodoro")
@Slf4j
public class PomoController {

    private User currentUser;

    private static final String REDIRECTURL ="redirect:/pomodoro/viewTasks";
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PomodoroService pomodoroService;

    @Autowired
    private MapperService mapperService;

    @GetMapping("/index")
    public String index(){
        return "redirect:/pomodoro/phome";
    }

    @GetMapping("/")
    public String getHome(Model theModel, Authentication authentication){
        log.info(">>>>> INSIDE GETHOME METHOD: AUTHENTICATION OBJECT: "+ authentication.getName());
        this.currentUser = userService.findByEmail(authentication.getName());
        return "pomodoro/phome";
    }

    @GetMapping("/createTask")
    public String showTaskForm(Model theModel){
        Task theTask = new Task();
        theModel.addAttribute("task", theTask);
        return "pomodoro/task-form";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("theTask") TaskDto theTaskDto){
        log.info(">>>>> The task details: " + theTaskDto.toString());

        //convert taskdto to task entity
        Task theTask = mapperService.convertToEntity(theTaskDto);
        log.info(">>>>> Current user: " + this.currentUser.getFirstName() + this.currentUser.getId());
        theTask.setUser(this.currentUser);
        taskService.save(theTask);
        this.currentUser.setTaskList(Arrays.asList(theTask));
        return REDIRECTURL;
    }

    @GetMapping("/viewTasks")
    public String showAllTasks(Model theModel){
        List<Task> tasks = taskService.findByUserId(this.currentUser.getId());
        log.info(">>>>> Tasks size: "+tasks.size());
        if (tasks.isEmpty()){
            throw new TaskNotFoundException();
        }
        else {
            theModel.addAttribute("tasks", tasks);
            return "pomodoro/view-tasks";
        }
    }

    @GetMapping("/removeTask")
    //bug notes: expected bug here
    public String removeTask(@RequestParam("taskId") int taskId, Model theModel){
        try {
            Task task = taskService.findById(taskId);
            log.info("Task to be deleted " + task.getTitle() + task.getId());
            pomodoroService.deleteByTaskId(taskId);
            this.currentUser.deleteTask(task);
            taskService.deleteByTaskId(taskId);
            log.info(">>>>> The current user task List size: " + this.currentUser.getTaskList().size());
            for(Task t: this.currentUser.getTaskList()){
                log.info("t.name: " + t.getTitle());
            }
            log.info(">>>>> INSIDE REMOVE TASK: before save() method");
        }
        catch (Exception e){
            log.info(">>>>> POMODORO CONTROLLER removeTask");
        }
        return REDIRECTURL;
    }

    @GetMapping("/startTask")
    public String startTask(@RequestParam("taskId") int taskId, Model theModel){
        //get the current time
        log.info(">>>>> starting time: " + LocalTime.now());
        //get the task
        Task task = taskService.findById(taskId);
        // check if the task has any pomodoros
        if(!task.getPomodoroList().isEmpty()){
            log.info(">>>>> The pomodoros are : " + task.getPomodoroList().toArray());
            theModel.addAttribute("pomodoro",task.getPomodoroList().get(0));
        }
        else {
            Pomodoro pomodoro = new Pomodoro();
            pomodoro.setStartTime(Time.valueOf(LocalTime.now()));
            pomodoro.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(25L)));
            pomodoro.setStatus(0);
            pomodoro.setDate(Date.valueOf(LocalDate.now()));
            task.addPomodoro(pomodoro);
            pomodoroService.save(pomodoro);
            theModel.addAttribute("pomodoro",pomodoro);
        }
        theModel.addAttribute("task", task);
        return "dojo/pomo-mode";
    }

    @GetMapping("/markComplete")
    public String markAsComplete(@RequestParam("taskId") int taskId, Model theModel){
        log.info(">>>>> task id: " + taskId + "user id " + this.currentUser.getId() + this.currentUser.getFirstName());
        Task currentTask=null;
        currentTask = taskService.findById(taskId);
        if(currentTask == null){
            throw new TaskNotFoundException();
        }
        List<Pomodoro> pomos = pomodoroService.findByTaskId(taskId);
        log.info(">>>>> Pomos status: " + pomos.get(0).getStatus());
        for(Pomodoro pomo: pomos) {
            log.info(">>>>> pomo details " + pomo.getId());
            pomo.setStatus(1);
            pomodoroService.save(pomo);
        }
        currentTask.setStatus(true);
        taskService.save(currentTask);
        return REDIRECTURL;
    }
}
