package com.jy.store.service;

import com.jy.store.entity.User;
import com.jy.store.mapper.UserMapper;
import com.jy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Provider;

@SpringBootTest

// @RunWith: To run with the unit test, need a parameter, it has to be SpringRunner class
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    /**
     * Unit test: no need to run the whole project, can test only run function, increase efficiency
     * 1. need to use @Test
     * 2. return type must be void
     * 3. the method have to be public
     * 4. no parameter
     *
     * **/

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("benben02");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("benben02","123456");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(8,"benben02","123456", "123");
    }
}
