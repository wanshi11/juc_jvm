package com.wanshi.interview.queues;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
 *
 * 1.线程操作资源类
 * 2.判断 干活 通知
 * 3.防止虚假唤醒机制(因为不是精确唤醒，要用while判断)
 * */
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();


        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.decrement();
            }
        }, "AAAA").start();


        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.increment();
            }
        }, "BBBB").start();
    }


}

class ShareData{
    int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void increment(){

        try{
            lock.lock();
            //判断
            while (number != 0){
                condition.await();
            }
            //干活
            number++;
            System.out.println("线程名："+Thread.currentThread().getName()+"\t"+"完成。increment。。number:"+number);
            //通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement(){
        try{
            lock.lock();
            //判断
            while (number == 0){
                condition.await();
            }
            //干活
            number--;
            System.out.println("线程名："+Thread.currentThread().getName()+"\t"+"完成。decrement。。number:"+number);
            //通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }


}
