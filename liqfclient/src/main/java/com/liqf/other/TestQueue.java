package com.liqf.other;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue {
	public static void main(String[] args) { 
    Queue<Integer> q = new LinkedBlockingQueue<Integer>(); 
    //初始化队列 
    for (int i = 0; i < 5; i++) { 
            q.offer(i); 
    } 
    System.out.println("-------1-----"); 
    //集合方式遍历，元素不会被移除 
    for (Integer x : q) { 
            System.out.println(x); 
    } 
    System.out.println("-------2-----"); 
    //队列方式遍历，元素逐个被移除 
    //while (q.peek() != null) { 
    for(int i = 0; i < 2; i ++){
            System.out.println(q.poll()); 
    } 
    System.out.println("-------3-----"); 
  //集合方式遍历，元素不会被移除 
    for (Integer x : q) { 
            System.out.println(x); 
    } 
} 
}
