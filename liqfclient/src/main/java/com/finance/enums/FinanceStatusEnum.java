package com.finance.enums;

/**
 * Created by jillkzh on 2016/1/17.
 */
public enum FinanceStatusEnum {
    START("持有", 1),
    END("结束", 2);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private FinanceStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (FinanceStatusEnum c : FinanceStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
