package com.spring.framework.ioc;

import com.google.common.base.Strings;
import com.spring.framework.config.model.BeanDefinition;
import com.spring.framework.config.model.Property;
import com.spring.framework.config.parser.ConfigParser;
import com.spring.framework.config.parser.XmlConfigParser;
import com.spring.framework.exceptions.BeanCreationException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-09 16:54
 **/
public class ClassPathXmlApplicationContext implements BeanFactory {
    /**
     * bean的定义
     */
    private Map<String, BeanDefinition> beanDefinitions;
    /**
     * IOC容器
     */
    private Map<String, Object> container;

    public ClassPathXmlApplicationContext(String configLocation) {
        //加载并解析XML
        ConfigParser parser = new XmlConfigParser();
        beanDefinitions = parser.parse(configLocation);
        //实例化bean，放入容器
        init();
    }

    /**
     * @Description: 初始化容器 描述
     * @Param: [beanDefinitions, container] 参数
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: xuan
     * @Date: 2019/5/10
     */

    private Map<String, Object> init() {
        for (BeanDefinition definition : beanDefinitions.values()) {
            container.put(definition.getId(), cteateBean(definition));
        }
        return null;
    }

    private Object cteateBean(BeanDefinition definition) {
        String type = definition.getType();
        try {
            Class<?> aClass = Class.forName(type);
            //创建实例
            Object instance = aClass.newInstance();
            //对属性进行依赖注入(Dependency injection)
            List<Property> properties = definition.getProperties();
            if (properties != null && !properties.isEmpty()) {
                for (Property p : properties) {
                    String fieldName = p.getName();
                    Field field = aClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    String value = p.getValue();
                    String ref = p.getRef();
                    //property使用的是value
                    if (!Strings.isNullOrEmpty(value) && Strings.isNullOrEmpty(ref)) {
                        field.set(instance, value);
                    }
                    //property使用的是ref
                    if (Strings.isNullOrEmpty(value) && !Strings.isNullOrEmpty(ref)) {
                        //判断容器是否有ref的bean
                        //有则用
                        //无则创建
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new BeanCreationException("无法加载指定的类:" + type, e);
        } catch (Exception e) {
            throw new BeanCreationException("创建bean出错:", e);
        }
        return null;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        //获取bean
        Object obj = container.get(beanName);
        if (obj == null) {
            return null;
        }
        if (!beanType.isAssignableFrom(obj.getClass())) {
            throw new RuntimeException("bean[" + beanName + "]不是指定的类型");

        }

        return (T) obj;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }
}
