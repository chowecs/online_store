package com.jy.store.mapper;

import com.jy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest

// @RunWith: To run with the unit test, need a parameter, it has to be SpringRunner class
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * Unit test: no need to run the whole project, can test only run function, increase efficiency
     * 1. need to use @Test
     * 2. return type must be void
     * 3. the method have to be public
     * 4. no parameter
     *
     * **/

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("Tim");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("Tim");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(1, "321", "admin", new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(1));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(7);
        user.setPhone("12345678");
        user.setEmail("bilibili@gmail.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
}
