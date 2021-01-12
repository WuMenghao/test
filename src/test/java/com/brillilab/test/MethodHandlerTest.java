package com.brillilab.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlerTest {

    class GrandFather{
        void thinking(){
            System.out.println("I am grandFather!");
        }
    }

    class Father extends GrandFather{
        @Override
        void thinking(){
            System.out.println("I am father!");
        }
    }

    class Son extends Father{
        @Override
        void thinking(){
            try {
                MethodType methodType = MethodType.methodType(void.class);
                MethodHandle methodHandle = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", methodType, getClass());
                methodHandle.invoke(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Son son =  new MethodHandlerTest().new Son();
        son.thinking();
    }

}
