package com.brillilab.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class DataStructureTest {

    @Test
    public void treeSetTest(){
        TreeSet<Integer> treeSet=new TreeSet<>();
        Integer[] integers={1,20,3,4,15,6,7,8,19,10,11,12,13,14,5,16,17,18,9,2};
        List<Integer> data=Arrays.asList(integers);

        treeSet.addAll(data);

        System.out.println(treeSet);
    }
}
