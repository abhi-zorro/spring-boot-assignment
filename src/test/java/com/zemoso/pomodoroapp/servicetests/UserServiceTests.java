package com.zemoso.pomodoroapp.servicetests;

import com.zemoso.pomodoroapp.dao.UserRepository;
import com.zemoso.pomodoroapp.entity.User;
import com.zemoso.pomodoroapp.service.UserService;
import com.zemoso.pomodoroapp.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTests {

    private UserRepository userRepository;
    private UserService userService;

    private User user;

    @BeforeEach
    void setup(){
        user = user.builder().
                firstName("Benjamn")
                .lastName("Doe")
                .email("benja@zemosolabs.com")
                .password("test")
                .build();
    }

//    @BeforeEach
//    void setUp() throws Exception {
//        userRepository = mock(UserRepository.class);
//        userService = new StudentServiceImpl(studentRepository);
//    }


    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for save User method")
    @Test
    void givenUserObject_whenSaveUser_thenReturnUserObject(){
        // given - precondition or setup

        given(userRepository.save(user)).willReturn(user);

        System.out.println(userRepository);
        System.out.println(userService);

        // when -  action or the behaviour that we are going test
        userService.save(user);

        System.out.println(user);
        // then - verify the output
        assertThat(user).isNotNull();
    }

}
