package com.wanshi.interview.locks;

import sun.applet.Main;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。
 * 使用Semaphore可以控制同时访问资源的线程个数.
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        //模拟三个车位
        Semaphore semaphore = new Semaphore(3);  //创建信号量并允许三个许可

        //10个线程模拟10部汽车争取三个车位
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("线程名（汽车编号）："+Thread.currentThread().getName()+"抢到车位啦！");
                    TimeUnit.SECONDS.sleep(3);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println("线程名（汽车编号）："+Thread.currentThread().getName()+"让出车位啦。。。。。。");
                    semaphore.release();
                }


            },String.valueOf(i)).start();
        }


    }

}
