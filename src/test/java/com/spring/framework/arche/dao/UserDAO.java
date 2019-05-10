package com.spring.framework.arche.dao;


import com.spring.framework.arche.dao.model.User;

import java.util.List;

public interface UserDAO {

    User findByUsernameAndPassword(String username, String password);

    List<User> findAll();
}
