package com.proxy.strategy;

/**
 * 
 * ²ßÂÔÖ´ÐÐ
 * @author weique.lqf
 * @version $Id: Context.java, v 0.1 2014-2-9 ÏÂÎç2:32:56 weique.lqf Exp $
 */
public class Context {
    private Strategy stg;

    public Context(Strategy theStg) {
        this.stg = theStg;
    }

    public void doAction() {
        this.stg.testStrategy();
    }
}
