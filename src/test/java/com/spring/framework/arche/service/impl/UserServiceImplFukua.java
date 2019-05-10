package com.spring.framework.arche.service.impl;



import com.chinasofti.framework.arche.dao.UserDAO;
import com.chinasofti.framework.arche.dao.model.User;
import com.chinasofti.framework.arche.service.UserService;
import com.chinasofti.framework.arche.web.UserController;

public class UserServiceImplFukua implements UserService {

    private UserDAO userDAO;


    @Override
    public String login(String username, String password) {

        User user = userDAO.findByUsernameAndPassword(username,password);
        return user!=null?"[]~(￣▽￣)~*成功":"o(╥﹏╥)o失败";
    }


    public void setUserDAO(UserDAO userDAO) {
        System.out.println("调用setUserDAO");
        this.userDAO = userDAO;
    }

    public void setUserController(UserController userController) {
        System.out.println("调用UserController");
    }
}
