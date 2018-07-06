package com.brillilab.test;

import org.junit.Test;

import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetTest {

    @Test
    public void test01(){
        TreeSet<String> treeSet=new TreeSet<>();
        treeSet.add("name=dasd");
        treeSet.add("age=20");
        treeSet.add("address=dsdsdsdsdsdds");

        System.out.println(treeSet);
    }

    @Test
    public void test02(){
        TreeSet<String> treeSet=new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length()<o2.length() ? 0 : -1;
            }
        });
        treeSet.add("name=dasd");
        treeSet.add("age=20");
        treeSet.add("address=dsdsdsdsdsdds");

        System.out.println(treeSet);
    }
}
