package com.spring.framework.config.model;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-09 16:46
 **/
@Data
public class Property {
    private String name;
    private String ref;
    private String value;
    private Field field;

    public Property(String name, String ref, String value) {
        this.name = name;
        this.ref = ref;
        this.value = value;
    }
}
