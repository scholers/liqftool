package com.proxy.strategy;

public class ContextSpring {
    private Strategy stg;

    /**
     * Setter method for property <tt>stg</tt>.
     * 
     * @param stg value to be assigned to property stg
     */
    public void setStg(Strategy stg) {
        this.stg = stg;
    }

    public void doAction() {
        this.stg.testStrategy();
    }
}
