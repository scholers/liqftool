package com.liqf.other;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue {
	public static void main(String[] args) { 
    Queue<Integer> q = new LinkedBlockingQueue<Integer>(); 
    //��ʼ������ 
    for (int i = 0; i < 5; i++) { 
            q.offer(i); 
    } 
    System.out.println("-------1-----"); 
    //���Ϸ�ʽ������Ԫ�ز��ᱻ�Ƴ� 
    for (Integer x : q) { 
            System.out.println(x); 
    } 
    System.out.println("-------2-----"); 
    //���з�ʽ������Ԫ��������Ƴ� 
    //while (q.peek() != null) { 
    for(int i = 0; i < 2; i ++){
            System.out.println(q.poll()); 
    } 
    System.out.println("-------3-----"); 
  //���Ϸ�ʽ������Ԫ�ز��ᱻ�Ƴ� 
    for (Integer x : q) { 
            System.out.println(x); 
    } 
} 
}
