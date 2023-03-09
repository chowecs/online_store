package com.jy.store.service;


import com.jy.store.entity.User;

public interface IUserService {
    void reg(User user);

    User login(String username, String password);

    void changePassword(Integer uid, String username, String oldPassword, String newPassword);
}
