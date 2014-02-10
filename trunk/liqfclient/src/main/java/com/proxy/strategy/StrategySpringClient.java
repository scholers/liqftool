package com.proxy.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StrategySpringClient {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        ContextSpring ct = (ContextSpring) context.getBean("ct");
        ct.doAction();
    }

}
