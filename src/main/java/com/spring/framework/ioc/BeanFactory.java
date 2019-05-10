package com.spring.framework.ioc;

/**
 * @program: IOCsmall
 * @description: Spring bean的工厂
 * @author: xuan
 * @create: 2019-05-09 16:51
 **/
public interface BeanFactory {
    /**
     * @Description: 从容器中获取到指定的bean 描述
     * @Param: [beanName, beanType] 参数
     * @return: T
     * @Author: xuan
     * @Date: 2019/5/9
     */
    <T> T getBean(String beanName, Class<T> beanType);

    /**
     * @Description: 判断容器中是否包含该bean 描述
     * @Param: [name] 参数
     * @return: boolean
     * @Author: xuan
     * @Date: 2019/5/9
     */
    boolean containsBean(String name);
}
