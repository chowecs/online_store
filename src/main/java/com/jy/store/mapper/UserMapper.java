package com.jy.store.mapper;

import com.jy.store.entity.User;

import java.util.Date;

public interface UserMapper {
    Integer insert(User user);

    User findByUsername(String name);

    //use uid to change user password
    //return value is affected row
    //password is new password that user entered
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    User findByUid(Integer uid);

    //update user information
    //return values is affected rows
    Integer updateInfoByUid(User user);

}
