package com.spring.framework.config.parser;

import com.spring.framework.config.model.BeanDefinition;

import java.util.Map;

/**
 * @program: IOCsmall
 * @description: XML解析器
 * @author: xuan
 * @create: 2019-05-09 16:48
 **/
public interface ConfigParser {
    /**
    * @Description: 从指定位置解析配置并生成beanDefinition
    * @Param: [configLocation]
    * @return: bean的id和BeanDefinition的映射关系
    * @Author: xuan
    * @Date: 2019/5/9
    */
    Map<String, BeanDefinition> parse(String configLocation);
}
