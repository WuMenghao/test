package com.brillilab.oop;

/**
 * 静态代码块
 * 代码块
 * 构造方法测试
 */
class ObjectA{
    {
        System.out.println("code block before static code block");
    }

    static {
        System.out.println("static code block");
    }

    {
        System.out.println("code block before constructor method");
    }

    public ObjectA() {
        System.out.println("constructor method");
    }

    {
        System.out.println("code block after constructor method");
    }

    public static void main(String[] args) {
        ObjectA objectA = new ObjectA();
    }

}