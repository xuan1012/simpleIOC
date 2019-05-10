package com.spring.framwork;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.Iterator;

/**
 * Unit test for simple App.
 */
public class Dom4jTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        SAXReader saxReader=new SAXReader();
        try {
            //得到文章对象，包含了所有的子节点
            Document document=saxReader.read(this.getClass().getResourceAsStream("/test.xml"));
            //获得根节点
            Element root =document.getRootElement();
            //遍历
            Iterator<Element> iterator=root.elementIterator();
            //1级遍历
            while (iterator.hasNext()){
                Element level1Element =iterator.next();
                System.out.println(level1Element.getName());
                //2级遍历
                Iterator<Element> level2Iterator = level1Element.elementIterator();
                while (level2Iterator.hasNext()){
                    Element level2Element=level2Iterator.next();
                    System.out.println("\t"+level2Element.getName());
                    if (level2Element.getName().equals("studentNo")){
                        //获取文字内容
                        String studentNo=level2Element.getText();
                        System.out.println("\t\t学号："+studentNo);

                    }
                    if (level2Element.getName().equals("name")){
                        System.out.println("\t\t姓名："+level2Element.getText());

                    }
                }
            }
        }catch (DocumentException e){
            e.printStackTrace();
            fail();
        }
    }
}
