package com.liqf.other;

import java.util.Stack;

public class TestStack { 
    public static void main(String[] args) { 
        Stack<Integer> s = new Stack<Integer>(); 
        for (int i = 0; i < 10; i++) { 
                s.add(i); 
        } 
        //���ϱ�����ʽ 
        for (Integer x : s) { 
                System.out.println(x); 
        } 
        System.out.println("------1-----"); 
        //ջ����������ʽ 
//        while (s.peek()!=null) {     //����׳���жϷ�ʽ���������쳣����ȷд��������� 
        //while (!s.empty()) { 
        for(int i = 0; i < 5; i ++) {
        	Integer name = s.pop();
             s.remove(name);
                System.out.println(name); 
        } 
        System.out.println("------2-----"); 
        //���ϱ�����ʽ 
        for (Integer x : s) { 
                System.out.println(x); 
        } 
        //����ı�����ʽ 
//        for (Integer x : s) { 
//                System.out.println(s.pop()); 
//        } 
} 
}