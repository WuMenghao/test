package com.brillilab.annotation;

/**
 *  容器中bean的存储类
 *  @author wmh
 */
public class BeanDefine {

    private String id;

    private String className;

    public BeanDefine(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "BeanDefine{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
