package com.wanshi.interview.thread.volatiledemo1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2 验证volatile不保证原子性
 * 2.1 原子性是不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者分割。
 * 需要整体完成，要么同时成功，要么同时失败。
 * <p>
 * 2.2 volatile不可以保证原子性演示
 * <p>
 * 2.3 如何解决原子性
 * 加sync
 * 使用我们的JUC下AtomicInteger
 */
public class VolatileDemoB {


    public static void main(String[] args) {

        MyDataB myDataB =new MyDataB();

        for (int i = 0; i < 20; i++) {

            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myDataB.add();
                    myDataB.addPlus();
                }
            },String.valueOf(i)).start();
        }

        //MAIN 1  GC 1,当大于2时，说明业务线程没有跑完
        while (Thread.activeCount() > 2){
            //当前线程让出CPU执行权 到可执行状态，但是不一定能一定让出
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finnally number value: "+myDataB.number);
        System.out.println(Thread.currentThread().getName()+"\t finnally number value: "+myDataB.atomicInteger);

    }



}


class MyDataB{

    //请注意，此时number前面是加了volatile关键字修饰的，volatile不保证原子性
    volatile int number;

    public void add(){
        number++;
    }

    //=====================
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addPlus(){
        atomicInteger.getAndIncrement();
    }





}
