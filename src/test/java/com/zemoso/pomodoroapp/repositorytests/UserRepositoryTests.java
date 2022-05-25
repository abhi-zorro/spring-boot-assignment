package com.zemoso.pomodoroapp.repositorytests;

import com.zemoso.pomodoroapp.dao.UserRepository;
import com.zemoso.pomodoroapp.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup(){
        user = user.builder().
                firstName("John")
                .lastName("Doe")
                .email("johndoe@zemoso.com")
                .password("test")
                .build();
    }

    // JUnit test for save employee operation
    @DisplayName("JUnit test for save user operation")
    @Test
    void givenUserObject_whenSave_thenReturnSavedUser(){

        //given - precondition or setup
        user = user.builder().
                firstName("Benjamin")
                .lastName("Doe")
                .email("benja@zemoso.com")
                .password("test")
                .build();

        // when - action or the behaviour that we are going test
        User savedUser = userRepository.save(user);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isPositive();
    }

    // JUnit test for get all users operation
    @DisplayName("JUnit test for get all users operation")
    @Test
    void givenUserList_whenFindAll_thenUserList(){
        // given - precondition or setup
        //given - precondition or setup
        User user1 = user.builder().
                firstName("Jonny")
                .lastName("Grace")
                .email("jonny@zemoso.com")
                .password("test")
                .build();

        userRepository.save(user);
        userRepository.save(user1);

        // when -  action or the behaviour that we are going test
        List<User> userList = userRepository.findAll();

        for(User user: userList){
            System.out.println("user " + user.getFirstName());
        }
        // then - verify the output
        assertThat(userList).isNotNull().hasSize(10);
    }

}
