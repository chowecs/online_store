package com.jy.store.controller;

import com.jy.store.entity.User;
import com.jy.store.service.IUserService;
import com.jy.store.service.ex.InsertException;
import com.jy.store.service.ex.UsernameDuplicatedException;
import com.jy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@Controller
@RestController //Controller + ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    IUserService userService;

    //This method respond json format to frontend
//    @RequestMapping("reg")
//    public JsonResult<Void> reg (User user){
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("Registration succeeded!");
//        } catch (UsernameDuplicatedException e) {
//            result.setState(4000);
//            result.setMessage("Username duplicated!");
//        } catch (InsertException e) {
//            result.setState(5000);
//            result.setMessage("Unknown exception during registration");
//        }
//        return result;
//    }
    @RequestMapping("reg")
    public JsonResult<Void> reg (User user){
        JsonResult<Void> result = new JsonResult<>();
        //Any Exception will automatically jump to the exception handler in base class
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login (String username, String password, HttpSession session){
        User data = userService.login(username, password);

        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }
}
