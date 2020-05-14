package com.brillilab.entity.bean;

import com.brillilab.annotation.AutoWiredTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanOne {

    @Autowired
    @AutoWiredTest(name = "beanTwo")
    private Bean beanTwo;

    @Autowired
    @AutoWiredTest(name = "beanThree")
    private Bean beanThree;

    @AutoWiredTest
    private Bean bean;

    public BeanOne() {}

    public void showAll(){
        beanTwo.show();
        beanThree.show();
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
