package com.spring.framework.arche.dao.impl;

import com.spring.framework.arche.common.DBHelper;
import com.spring.framework.arche.dao.UserDAO;
import com.spring.framework.arche.dao.model.User;
import com.spring.framework.config.annotation.Repository;
import com.spring.framework.config.annotation.Value;

import java.util.List;
import java.util.Map;
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    @Value("123")
    private String age;

    private Map<String,Object> data;

    private DBHelper.Extractor<User> userExtractor = rs -> {
        int userId = rs.getInt(1);
        String username = rs.getString(2);
        User user = new User(username);

        return user;
    };

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        System.out.println("注入的age值为："+age);
        System.out.println("注入的data值为："+data);



        return new User(username);
    }

    @Override
    public List<User> findAll() {
        DBHelper dbHelper = new DBHelper();
        List<User> users = dbHelper.query("select * from emall_user", userExtractor);

        return users;
    }

}
