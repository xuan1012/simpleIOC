package com.spring.framework.config.model;

import lombok.Data;

import java.util.List;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-09 16:42
 **/
@Data
public class BeanDefinition {
    private String id;
    private String type;
    private List<Property> properties;
    private Class<?> typeClass;
}
