package com.brillilab.annotation;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class SimpleClassPathXMLApplicationContext {

    Logger log=Logger.getLogger(SimpleClassPathXMLApplicationContext.class);

    List<BeanDefine> beanlist=new ArrayList<BeanDefine>();
    Map<String,Object> sigletions=new HashMap<String, Object>();

    public SimpleClassPathXMLApplicationContext(String fileName) {
        //(1)读取配置文件中管理的Bean
        this.readXML(fileName);
        //(2)实例化Bean
        this.instancesBean();
        //(3)注解处理
        this.annotationInject();
    }

    /**
     * 读取Bean配置文件
     * @param fileName
     * @return
     */
    @SuppressWarnings("unchecked")
    private void readXML(String fileName) {
        Document document=null;
        SAXReader saxReader=new SAXReader();
        try {
            //(1)获取类加载器
            ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            //(2)使用SAX解析器解析xml文件,将bean信息添加至beanlist中
            document=saxReader.read(classLoader.getResourceAsStream(fileName));
            Element beans=document.getRootElement();
            for (Iterator<Element> elements=beans.elementIterator();
                 elements.hasNext();){
                Element element = elements.next();
                BeanDefine bean=new BeanDefine(
                        element.attributeValue("id"),
                        element.attributeValue("class"));
                beanlist.add(bean);
            }
        } catch (DocumentException e) {
            log.info("读取配置文件出错....");
        }
    }

    /**
     * 实例化Bean
     */
    private void instancesBean() {
        for (BeanDefine bean:beanlist){
            try {
                sigletions.put(bean.getId(),Class.forName(bean.getClassName()).newInstance());
            } catch (Exception e) {
                log.info("实例化Bean出错...");
            }
        }
    }

    /**
     * 注解处理
     *      如果AutoWired注解name属性有赋值，则根据name所指定的名称注入bean
     *      如果AutoWired注解name属性未被赋值，则根据属性所属类型来扫描配置文件注入bean
     */
    private void annotationInject() {
        for (String beanName:sigletions.keySet()){
            Object bean=sigletions.get(beanName);
            if (bean!=null){
                //(1)set方法注入
                this.propertyAnnotation(bean);
                //(2)filed注入
                this.fieldAnnotation(bean);
            }
        }
    }

    /**
     * filed注入
     * @param bean
     */
    private void fieldAnnotation(Object bean) {
        try {

            //(1)反射获取bean的全部字段
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field:fields){
                if (field!=null && field.isAnnotationPresent(AutoWiredTest.class)){
                    AutoWiredTest autoWired = field.getAnnotation(AutoWiredTest.class);
                    String name=null;
                    Object value=null;
                    //
                    if (autoWired.name()!=null && !"".equals(autoWired.name())){
                        name=autoWired.name();
                        value=sigletions.get(name);
                    }else {
                        for (String key:sigletions.keySet()){
                            if (field.getType().isAssignableFrom(sigletions.get(key).getClass())){
                                value=sigletions.get(key);
                                break;
                            }
                        }
                    }
                    //允许访问private字段
                    field.setAccessible(true);
                    //把引用对象注入字段
                    field.set(bean,value);
                }
            }
        }catch (Exception e){
            log.info("字段注解解析异常..........");
        }
    }

    /**
     * set注入
     * @param bean
     */
    private void propertyAnnotation(Object bean) {
        try {
            //(1)内省获得Bean的属性信息
            PropertyDescriptor[] ps=Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor propDes:ps){
                //(2)获取属性对应的setter方法
                Method setter = propDes.getWriteMethod();
                //(3)有AutoWired注解则注入
                if (setter!=null && setter.isAnnotationPresent(AutoWiredTest.class)){
                    AutoWiredTest autoWired = setter.getAnnotation(AutoWiredTest.class);
                    String name=null;
                    Object value=null;
                    //获取bean，有名字，没名字
                    if (autoWired.name()!=null && !"".equals(autoWired.name())){
                        name=autoWired.name();
                        value=sigletions.get(name);
                    }else {
                        for (String key:sigletions.keySet()){
                            //匹配第一个符合的bean
                            if (propDes.getPropertyType().isAssignableFrom(sigletions.get(key).getClass())){
                                value=sigletions.get(key);
                                break;
                            }
                        }
                    }
                    //允许访问private方法
                    setter.setAccessible(true);
                    //把引用对象注入属性
                    setter.invoke(bean,value);
                }
            }
        } catch (Exception e) {
            log.info("set方法注解解析异常..........");
        }
    }

    /**
     * 获取bean
     * @param beanId
     * @return
     */
    public Object getBean(String beanId){
        return sigletions.get(beanId);
    }
}
