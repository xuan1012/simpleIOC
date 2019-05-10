package com.spring.framework.arche.service.impl;

import com.chinasofti.framework.arche.dao.UserDAO;
import com.chinasofti.framework.arche.dao.model.User;
import com.chinasofti.framework.arche.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Override
    public String login(String username, String password) {

        User user = userDAO.findByUsernameAndPassword(username,password);

        return user!=null?"成功":"失败";
    }


    public void setUserDAO(UserDAO userDAO) {
        System.out.println("调用setUserDAO");
        this.userDAO = userDAO;
    }
}