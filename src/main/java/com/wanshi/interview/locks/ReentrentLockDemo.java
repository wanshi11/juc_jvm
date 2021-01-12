package com.wanshi.interview.locks;

import java.util.concurrent.locks.ReentrantLock;


/*
 * 可重入锁（也就是递归锁）：指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个
 * 它已经拥有的锁所有同步着的代码块。
 * */
public class ReentrentLockDemo {

    public static void main(String[] args) {
        PhoneRL phone =  new PhoneRL();
        Thread t1 = new Thread(phone,"t1");
        Thread t2 = new Thread(phone,"t2");
        t1.start();
        t2.start();
    }
}

class PhoneRL implements  Runnable{

    private ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void run() {
        sendMessage();
    }

    public void   sendMessage(){
        try {
            reentrantLock.lock();
            System.out.println("sendMessage Over!"+"\t"+Thread.currentThread().getName());
            sendmail();
        }finally {
            reentrantLock.unlock();
        }

    }


    public void   sendmail(){
        try{
            reentrantLock.lock();
            System.out.println("sendmail Over!"+"\t"+Thread.currentThread().getName());
        }finally {
            reentrantLock.unlock();
        }
    }

}
