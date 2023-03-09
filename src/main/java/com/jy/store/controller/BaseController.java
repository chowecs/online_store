package com.jy.store.controller;

import com.jy.store.service.ex.*;
import com.jy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    //Operation succeeded code
    public static final int OK = 200;

    //All exception will be handled by this method
    @ExceptionHandler(ServiceException.class) // Group all the exception
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("Username duplicated!");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("Unknown exception during registration");
        } else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("Username not exist");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("User password is wrong");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("Unknown exception update");
        }
        return result;
    }

    //Return uid of current login user
    protected final Integer getuidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    //Return username of current login user
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
