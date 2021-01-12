package com.wanshi.interview.locks;

import cn.hutool.core.math.MathUtil;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("线程名:"+Thread.currentThread().getName()+"\t 执行业务逻辑");
                try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
                System.out.println("线程名:"+Thread.currentThread().getName()+"\t 执行业务逻辑完成");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        System.out.println(Thread.currentThread().getName()+"\t 等待中");
        try{countDownLatch.await();}catch (Exception e){e.printStackTrace();}


        System.out.println(Thread.currentThread().getName()+"\t 等待完成，继续往下执行。。。");

    }


}
