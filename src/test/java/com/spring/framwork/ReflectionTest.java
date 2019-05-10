package com.spring.framwork;

import com.spring.framwork.data.Pig;
import junit.framework.TestCase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-10 10:30
 **/
public class ReflectionTest extends TestCase {

    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException {
        //app.class  | new App().getClass() | Class.forName("com.xxx.App")
        Class<Pig> pigClass =Pig.class;
        Pig p0=new Pig();
        p0.setName("111");
        Pig p1=new Pig();
        p0.setName("222");
        Method[] declaredMethods = pigClass.getDeclaredMethods();
        for (Method declaredMethod:declaredMethods){
            System.out.println(declaredMethod.getName());
        }
        Method getName = pigClass.getDeclaredMethod("getName");
        Object result0=getName.invoke(p0);
        System.out.println("getName:"+result0);
        Object result1=getName.invoke(p1);
        System.out.println("getName:"+result1);

        Field[] declaredFields = pigClass.getDeclaredFields();
        for (Field field:declaredFields){
            System.out.println(field.getName());
        }
        Field nameField = pigClass.getDeclaredField("name");
        //变成可访问的
        nameField.setAccessible(true);
        nameField.set(p0,"hong");
        String value= (String) nameField.get(p0);
        System.out.println(nameField.getName()+"值为:"+value);
        Constructor<?>[] declaredConstructors = pigClass.getDeclaredConstructors();
        for (Constructor<?> constructor:declaredConstructors){
            System.out.println(constructor.getName());
        }
        Pig pig = (Pig) Class.forName("com.spring.framwork.data.Pig").newInstance();
        pig.setName("ssss");
        pig.setWeight(2000);
        pig.setSpecies("Super");
        System.out.println(pig);


    }
}
