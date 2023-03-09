package com.jy.store.service.impl;

import com.jy.store.entity.User;
import com.jy.store.mapper.UserMapper;
import com.jy.store.service.IUserService;
import com.jy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null){
            throw new UsernameDuplicatedException("Username is duplicated!");
        }

        // Encrypt user password with MD5
        // salt + password + salt  --- MD5, salt is a random string
        String oldPassword = user.getPassword();
        // Generate a salt
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //If successful, rows == 1
        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("Unknown Exception during user registration!");
        }

    }

    @Override
    public User login(String username, String password) {
        // check whether username exist
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("Username not exist");
        }
        // check password correct or not
        //1. get the hashed password from database
        String oldPassword = result.getPassword();
        //2. compare the password
        //2.1 get salt
        String salt = result.getSalt();
        //2.2 MD5 hash the input password from user
        String newMd5Password = getMD5Password(password, salt);
        //Compare password
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("Wrong password!");
        }

        //determine whether the user is deleted
        if (result.getIsDelete() == 1 ){
            throw new UserNotFoundException("User not found!");
        }

        //can increase performance, if return all data of user will slow down system
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("User not Found");
        }
        //compare old password with database password
        String oldMD5Password = getMD5Password(oldPassword, result.getSalt());
        if(!oldMD5Password.equals(result.getPassword())){
            throw new PasswordNotMatchException("Wrong password");
        }
        //set new password into database
        String newMD5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(result.getUid(), newMD5Password, username, new Date());
        if(rows != 1){
            throw new UpdateException("Unknown exception during update");
        }
    }

    /** define a md5 encryption*/
    private String getMD5Password(String password, String salt) {
        //md5 encryption function for 3 times
        for (int i=0;i<3;i++){
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
