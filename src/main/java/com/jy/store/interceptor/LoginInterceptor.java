package com.jy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    //Check whether session has uid, if yes let it access
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //get uid from HttpServletRequest object
        Object obj = request.getSession().getAttribute("uid");
        if(obj==null){ // It means user doesn't login to system
            response.sendRedirect("/web/login.html");
            return false; //return false means intercept following actions
        }

        return true;
    }
}
