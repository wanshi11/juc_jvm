package com.wanshi.interview.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/*
 * 多个线程同时读一个资源类没有问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 *
 * 但是如果有一个线程想去写共享资源，就不应该再有其他线程可以对资源进行读或写
 *
 * 写操作：原子+独占，整个过程必须是一个完整的统一体，中间不许被分割，被打断。
 *
 * 小总结：
 *     读-读能共存
 *     读-写不能共存
 *     写-写不能共存
 * */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        Mycache cache = new Mycache();

        for (int i = 1; i <= 10; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.get(tempInt+"");
            },String.valueOf(i)).start();
        }

    }



}

class Mycache{
    private volatile Map<String,Object> map = new HashMap<>();
    //    private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void put(String key,String value){
        try {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+"\t"+"开始写入"+key);
            try{
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {e.printStackTrace();}
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t"+"写入完成"+value);
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    public void get(String key){
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+"\t"+"开始读取"+key);
            try{
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {e.printStackTrace();}
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t"+"读取完成"+result);
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}
