package com.spring.framework.arche.web;


import com.spring.framework.arche.service.UserService;

public class UserController {

    private UserService userService;


    public String login(String username,String password){

       String resultMessage =  userService.login(username,password);
        return resultMessage;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
