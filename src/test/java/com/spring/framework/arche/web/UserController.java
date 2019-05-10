package com.spring.framework.arche.web;


import com.spring.framework.arche.service.UserService;
import com.spring.framework.config.annotation.Autowired;
import com.spring.framework.config.annotation.Controller;

@Controller("userController")
public class UserController {
    @Autowired
    private UserService userService;


    public String login(String username,String password){

       String resultMessage =  userService.login(username,password);
        return resultMessage;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
