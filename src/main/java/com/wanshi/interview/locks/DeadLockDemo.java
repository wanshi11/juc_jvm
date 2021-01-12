package com.wanshi.interview.locks;

import java.util.concurrent.TimeUnit;


/**
 * 准备工作：
 *
 * 十进制转十六进制：printf “%x\n” pid
 * top -Hp pid
 * jps
 * 定位排查思路：
 * 1.使用top找到占用资源较多的进程id
 * 2.使用top -Hp 进程id 找到有问题的线程id
 * 3.jstack 进程id|grep -A 35 printf "%x" 线程id
 * 4.定位到具体的问题代码
 * 5.代码整改！
 *
 * 注意问题：
 * jstack使用时注意切换用户，使用什么用户起的程序就要使用什么用户运行jstack，否则可能出现错误
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLockDemo deadLockDemo = new DeadLockDemo();

        new Thread(new HoldLock(lockA,lockB),"AAAAA").start();

        new Thread(new HoldLock(lockB,lockA),"BBBBB").start();

    }

}

class HoldLock implements Runnable{

    private String lockA;
    private String lockB;


    public HoldLock(String lockA,String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"持有"+lockA);
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"尝试获取"+lockB);
            synchronized (lockB){

            }
        }
    }


}
