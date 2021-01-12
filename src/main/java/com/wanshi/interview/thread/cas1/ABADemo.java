package com.wanshi.interview.thread.cas1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;


/*
 * ABA问题的解决  AtomicStampedReference
 *
 * 问题，ABA问题出现，当第一次修改值（即第二次期望值）不是101时修改成199时，ABA没有出现？？？
 *
 *
 * */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100,1);




    public static void main(String[] args) {


        System.out.println("======以下是ABA问题的出现=====");
        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100,101)+"\t"+atomicReference.get());

            System.out.println(atomicReference.compareAndSet(101,100)+"\t"+atomicReference.get());
        },"t1").start();


        new Thread(() -> {
            //确保ABA操作完成
            try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}

            System.out.println( atomicReference.compareAndSet(100,2020) + "\t" + atomicReference.get());
        },"t2").start();


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("======以下是ABA问题的解决=====");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);

            //暂停1秒钟t3线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号：" + atomicStampedReference.getStamp()+"\t"+atomicStampedReference.getReference());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号：" + atomicStampedReference.getStamp()+"\t"+atomicStampedReference.getReference());
        }, "t3").start();


        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);

            //暂停1秒钟t4线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, atomicStampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName() + "\t修改成功否： " + result + "\t当前最新实际版本号：" + atomicStampedReference.getStamp()+"\t"+atomicStampedReference.getReference());

        }, "t4").start();
    }
}
