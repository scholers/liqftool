package com.liqf.other;

import java.util.Stack;

public class TestStack { 
    public static void main(String[] args) { 
        Stack<Integer> s = new Stack<Integer>(); 
        for (int i = 0; i < 10; i++) { 
                s.add(i); 
        } 
        //集合遍历方式 
        for (Integer x : s) { 
                System.out.println(x); 
        } 
        System.out.println("------1-----"); 
        //栈弹出遍历方式 
//        while (s.peek()!=null) {     //不健壮的判断方式，容易抛异常，正确写法是下面的 
        //while (!s.empty()) { 
        for(int i = 0; i < 5; i ++) {
        	Integer name = s.pop();
             s.remove(name);
                System.out.println(name); 
        } 
        System.out.println("------2-----"); 
        //集合遍历方式 
        for (Integer x : s) { 
                System.out.println(x); 
        } 
        //错误的遍历方式 
//        for (Integer x : s) { 
//                System.out.println(s.pop()); 
//        } 
} 
}