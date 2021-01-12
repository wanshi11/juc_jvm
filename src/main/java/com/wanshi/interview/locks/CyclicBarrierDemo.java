package com.wanshi.interview.locks;

import sun.applet.Main;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println("已经集齐7颗龙珠，开始召唤神龙....");
        });

        for (int i = 1; i <= 7; i++) {
            final  int tempInt = i;
            new Thread(() -> {
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){e.printStackTrace();}
                System.out.println("线程名："+Thread.currentThread().getName()+"收集到第"+tempInt+"颗龙珠!");

                //执行完逻辑后线程需要再屏障出等待，等待一组线程全到达后，屏障会打开
                try{cyclicBarrier.await();}catch (Exception e){e.printStackTrace();}
            },String.valueOf(i)).start();
        }




    }


}
