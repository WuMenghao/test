package com.brillilab.bean;

import com.brillilab.annotation.AutoWiredTest;

public class BeanOne {

    @AutoWiredTest(name = "beanTwo")
    private Bean beanTwo;

    @AutoWiredTest(name = "beanThree")
    private Bean beanThree;

    @AutoWiredTest
    private Bean bean;

    public BeanOne() {}

    public void showAll(){
        beanTwo.show();
        beanThree.show();
        bean.show();
    }

    public Bean getBeanTwo() {
        return beanTwo;
    }

    public void setBeanTwo(Bean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public Bean getBeanThree() {
        return beanThree;
    }

    public void setBeanThree(Bean beanThree) {
        this.beanThree = beanThree;
    }
}
