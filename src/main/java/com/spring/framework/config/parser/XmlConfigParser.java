package com.spring.framework.config.parser;

import com.spring.framework.config.annotation.*;
import com.spring.framework.config.model.BeanDefinition;
import com.spring.framework.config.model.Property;
import com.spring.framework.exceptions.ConfigParseException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.reflections.Reflections;

import java.lang.reflect.Field;
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
                    parseBean(definitionMap, bean);

                } else if (bean.getName().equals("component-scan")) {
                    parseComponentScan(definitionMap, bean);
                }
            }
            //TODO 验证文件配置的完整性
        } catch (DocumentException e) {
            throw new ConfigParseException("获取配置文件:" + configLocation + "出错");
        } catch (Exception e) {
            throw new ConfigParseException("解析配置文件出错", e);
        }
        return definitionMap;
    }

    private void parseComponentScan(Map<String, BeanDefinition> definitionMap, Element bean) {
        //得到扫描的基础包
        String basePackage = bean.attributeValue("base-package");
        //包扫描
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedClassed = new HashSet<>();
        annotatedClassed.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        annotatedClassed.addAll(reflections.getTypesAnnotatedWith(Service.class));
        annotatedClassed.addAll(reflections.getTypesAnnotatedWith(Repository.class));
        annotatedClassed.addAll(reflections.getTypesAnnotatedWith(Component.class));
        for (Class<?> c : annotatedClassed) {
            BeanDefinition def = new BeanDefinition();
            //id约定为类名首字母小写形式
            String id = getBeanName(c);
            StringUtils.uncapitalize(c.getSimpleName());
            def.setId(id);
            def.setTypeClass(c);
            List<Property> properties = new ArrayList<>();
            Field[] declaredFields = c.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Property P = new Property();
                    P.setName(field.getName());
                    P.setRef(field.getName());
                    P.setField(field);
                    properties.add(P);
                }
                if (field.isAnnotationPresent(Value.class)) {
                    Property p = new Property();
                    p.setName(field.getName());
                    Value annotation = field.getAnnotation(Value.class);
                    p.setValue(annotation.value());
                    p.setField(field);
                    properties.add(p);
                }
            }
            def.setProperties(properties);
            definitionMap.put(id, def);
        }

    }

    private String getBeanName(Class<?> c) {
        Controller controller = c.getAnnotation(Controller.class);
        Service service = c.getAnnotation(Service.class);
        Repository repository = c.getAnnotation(Repository.class);
        Component component = c.getAnnotation(Component.class);
        if (controller != null) {
            return controller.value();
        }
        if (service != null) {
            return service.value();
        }
        if (repository != null) {
            return repository.value();
        }
        if (component != null) {
            return component.value();
        }
        return null;
    }


    private void parseBean(Map<String, BeanDefinition> definitionMap, Element bean) {
        BeanDefinition beanDefinition = new BeanDefinition();
        String id = bean.attributeValue("id");
        beanDefinition.setId(id);
        String type = bean.attributeValue("class");
        beanDefinition.setType(type);
        //遍历levlel2的property
        List<Property> properties = new ArrayList<>();
        Iterator<Element> levelTwoIterator = bean.elementIterator();
        while (levelTwoIterator.hasNext()) {
            Element propElement = levelTwoIterator.next();
            String ref = propElement.attributeValue("ref");
            String value = propElement.attributeValue("value");
            String name = propElement.attributeValue("name");
            properties.add(new Property(name, ref, value));
        }
        beanDefinition.setProperties(properties);
        definitionMap.put(id, beanDefinition);
    }
}
