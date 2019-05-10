package com.spring.framwork.config.parser;

import com.spring.framwork.config.model.BeanDefinition;
import com.spring.framwork.config.model.Property;
import com.spring.framwork.exceptions.ConfigParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-09 16:50
 **/
public class XmlConfigParser implements ConfigParser {
    @Override
    public Map<String, BeanDefinition> parse(String configLocation) {
        Map<String, BeanDefinition> definitionMap = new ConcurrentHashMap<>();
        SAXReader saxReader = new SAXReader();
        try {
            //得到文档对象，包含了所有的子节点
            Document document = saxReader.read(this.getClass().getResourceAsStream("/" + configLocation));
            Element root = document.getRootElement();
            //遍历第一级 <bean>
            Iterator<Element> levelOneIterator = root.elementIterator();
            while (levelOneIterator.hasNext()) {
                Element bean = levelOneIterator.next();
                if (bean.getName().equals("bean")) {
                    BeanDefinition beanDefinition = new BeanDefinition();
                    String id = bean.attributeValue("id");
                    beanDefinition.setId(id);
                    String type = bean.attributeValue("class");
                    beanDefinition.setType(type);
                    //遍历levlel2的property
                    List<Property> properties=new ArrayList<>();
                    Iterator<Element> levelTwoIterator=bean.elementIterator();
                    while (levelTwoIterator.hasNext()){
                        Element propElement =levelTwoIterator.next();
                        String ref=propElement.attributeValue("ref");
                        String value=propElement.attributeValue("value");
                        String name =propElement.attributeValue("name");
                        properties.add(new Property(name,ref,value));
                    }
                    beanDefinition.setProperties(properties);
                    definitionMap.put(id, beanDefinition);

                }
            }
            //TODO 验证文件配置的完整性
        } catch (DocumentException e) {
            throw new ConfigParseException("获取配置文件:"+configLocation+"出错");
        }catch (Exception e){
            throw new ConfigParseException("解析配置文件出错",e);
        }
        return definitionMap;
    }
}
