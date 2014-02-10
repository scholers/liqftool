package com.proxy.strategy;

import com.proxy.strategy.impl.PrintStrategy;

public class StrategyClient {

    public static void main(String[] args) {
        Strategy stgA = new PrintStrategy();
        Context ct = new Context(stgA);
        ct.doAction();
    }
}
