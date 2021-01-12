package com.brillilab.test;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeTest {

    class A {
        private int a;
        private int b;
        Unsafe unsafe;

        {
            Field theUnsafe = null;
            try {
                theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                unsafe = (Unsafe) theUnsafe.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public A(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {

            long srcAddr = getAddr(this);
            long size = sizeOf(this.getClass());
            long destAddr = unsafe.allocateMemory(size);
            unsafe.copyMemory(srcAddr,destAddr,size);
            return fromAddress(destAddr,size);
        }

        private  long getAddr(Object obj) {
            Object[] array = new Object[]{obj};
            long baseOffset = unsafe.arrayBaseOffset(Object[].class);
            return unsafe.getLong(array, baseOffset);
        }

        private   long sizeOf(Class clazz) {
            long maximumOffset = 0;
            Class maxiNumFieldClass = null;
            do {
                for (Field f : clazz.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        long tmp = unsafe.objectFieldOffset(f);
                        if(tmp>maximumOffset){
                            maximumOffset = unsafe.objectFieldOffset(f);
                            maxiNumFieldClass = f.getType();
                        }
                    }
                }
            } while ((clazz = clazz.getSuperclass()) != null);
            long last = byte.class.equals(maxiNumFieldClass)?1:
                    ( short.class.equals(maxiNumFieldClass) || char.class.equals(maxiNumFieldClass))?2:
                            (long.class.equals(maxiNumFieldClass)||double.class.equals(maxiNumFieldClass))?8:4;
            return maximumOffset + last;
        }

        private  Object fromAddress(long addr, long size) {
            Object[] array = new Object[]{null};
            long baseOffset = unsafe.arrayBaseOffset(Object[].class);
            unsafe.putLong(array, baseOffset, addr);
            return (Object) array[0];
        }
    }

    @Test
    public void testCopyMemory() throws CloneNotSupportedException {
        A a = new A(1, 2);
        A b = (A)a.clone();
        System.out.println(a);
        System.out.println(b);
    }
}
