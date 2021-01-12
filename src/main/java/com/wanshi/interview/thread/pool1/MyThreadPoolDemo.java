package com.wanshi.interview.thread.pool1;

import lombok.val;

import java.util.concurrent.*;


/*第4种获得/使用java多线程的方式，通过线程池
        * （其他三种是：继承Thread类；实现Runnable接口，但是Runnable没有返回值，不抛异常；
        * 实现Callable接口，有返回值，会跑出异常）
        * */

//看电脑是几核的 System.out.println(Runtime.getRuntime().availableProcessors());
//Array Arrays  辅助工具类
//Collection Collections
//Executor Executors

public class MyThreadPoolDemo {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 0; i < 10; i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 开始执行业务逻辑。。。。");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭线程池
            executor.shutdown();
        }


        System.out.println(Thread.activeCount());
        try{TimeUnit.SECONDS.sleep(5);}catch (Exception e){e.printStackTrace();}

        //gc 和 main
        System.out.println(Thread.activeCount());
        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}