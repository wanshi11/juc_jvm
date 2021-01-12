package com.wanshi.interview.locks;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*
 * 写一个自旋锁
 * 自旋锁的好处：循环比较获取直到成功为止，没有类似wait的阻塞。
 *
 * 通过 CAS 操作完成自旋锁，
 * A 线程先进来调用 myLock 方法自己持有锁 5 秒钟，
 * B 随后进来发现当前有线程持有锁，不是 null， 所以只能通过自旋等待，直到 A 释放锁后 B 抢到
 * */
public class SpinLockDemo {

    private AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

    public  void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println("start try Lock...");
        while (!atomicReference.compareAndSet(null,thread)){
            System.out.println("加锁自旋中。。。当前线程Name："+Thread.currentThread().getName());
        }
        System.out.println("已获取锁。。。当前线程Name："+Thread.currentThread().getName());
    }

    public  void myunLock(){
        Thread thread = Thread.currentThread();
        System.out.println("come in myunLock...");
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();
        new Thread(() -> {
            try {
                demo.myLock();
                System.out.println(Thread.currentThread().getName()+"\t"+"T1 逻辑执行中");
                try{TimeUnit.SECONDS.sleep(3);}catch (Exception e){e.printStackTrace();}
            }finally {
                demo.myunLock();
            }
        },"T1").start();

        try{TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}

        new Thread(() -> {
            try {
                demo.myLock();
                System.out.println(Thread.currentThread().getName()+"\t"+"T2 逻辑执行中");
                try{TimeUnit.SECONDS.sleep(3);}catch (Exception e){e.printStackTrace();}
            }finally {
                demo.myunLock();
            }
        },"T2").start();

    }


}
