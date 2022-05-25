package com.zemoso.pomodoroapp.exception;

import com.zemoso.pomodoroapp.entity.Task;
import com.zemoso.pomodoroapp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public String UserNotFoundException(UserNotFoundException userNotFoundException, Model theModel){
        theModel.addAttribute("message","User not exists, please create user");
        return "/error";
    }
//    public ResponseEntity<Object> userNotFoundException(UserNotFoundException userNotFoundException){
//        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public String TaskNotFoundException(TaskNotFoundException taskNotFoundException, Model theModel){
        theModel.addAttribute("message","Task not Found, please create a task");
        return "/error";
    }
//    public ResponseEntity<Object> taskNotFound(TaskNotFoundException taskNotFoundException){
//        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
//    }
}
