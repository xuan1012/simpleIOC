package com.spring.framwork;

import com.spring.framwork.config.model.BeanDefinition;
import com.spring.framwork.config.parser.ConfigParser;
import com.spring.framwork.config.parser.XmlConfigParser;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-09 19:49
 **/
public class XmlConfigParserTest extends TestCase {
    @Test
    public void testParse() {
        try {
            ConfigParser parser = new XmlConfigParser();
            Map<String, BeanDefinition> definitionMap = parser.parse("applicationContext.xml");
            System.out.println(definitionMap);
            assertNotNull(definitionMap);
            assertTrue(definitionMap.size() == 2);
            BeanDefinition beanDefinition = definitionMap.get("userService");
            assertNotNull(beanDefinition);
            assertEquals("com.chinasofti.etc.spring.xml.service.impl.UserServiceImpl", beanDefinition.getType());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
