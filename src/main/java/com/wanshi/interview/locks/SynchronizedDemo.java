package com.wanshi.interview.locks;


/*
 * 可重入锁（也就是递归锁）：指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个
 * 它已经拥有的锁所有同步着的代码块。
 * */
public class SynchronizedDemo {

    public static void main(String[] args) {
        Phone phone =new Phone();

        new Thread(() -> {
            phone.sendmessage();
        },"T1").start();


        new Thread(() -> {
            phone.sendmessage();
        },"T2").start();

    }




}


class Phone{

    public  synchronized void sendmessage(){
        System.out.println("sendMessage Over!"+"\t"+Thread.currentThread().getName());
        sendmail();
    }

    public  synchronized  void sendmail(){
        System.out.println("sendmail Over!"+"\t"+Thread.currentThread().getName());
    }
}
