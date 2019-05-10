package com.spring.framework;

import com.spring.framework.arche.dao.UserDAO;
import com.spring.framework.arche.dao.model.User;
import com.spring.framework.arche.web.UserController;
import com.spring.framework.ioc.ClassPathXmlApplicationContext;
import junit.framework.TestCase;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-10 12:01
 **/
public class ApplicationContextTest extends TestCase {
    public void testInit(){
        try {
            ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
            UserDAO userDAO=ctx.getBean("userDAO",UserDAO.class);
            User zhangsan=userDAO.findByUsernameAndPassword("zhangsan","123");
            assertNotNull(zhangsan);
            UserController userController=ctx.getBean("userController",UserController.class);
            assertNotNull(userController);
            String resultMessage=userController.login("zhangsan","123");
            assertEquals("[]~(￣▽￣)~*成功",resultMessage);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
