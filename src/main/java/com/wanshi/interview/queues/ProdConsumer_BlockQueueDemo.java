package com.wanshi.interview.queues;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ShareSource{
    private AtomicInteger auAtomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;
    private volatile boolean flag = true; //开关，默认开启生产消费模式！


    public ShareSource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }


    public  void add() throws Exception {
        String data = null;
        boolean retValue;
        while (flag){
             data =  auAtomicInteger.getAndIncrement()+"";
             retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
//               retValue = blockingQueue.offer(data);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t"+"插入队列成功，插入"+data);
                    TimeUnit.SECONDS.sleep(1);
            }else{
                System.out.println(Thread.currentThread().getName()+"\t"+"插入队列失败！");
                return;
            }
        }
        System.out.println(Thread.currentThread().getName()+"\t生产停止");
    }

    public  void delete() throws  Exception{
        String elment;
        while (flag){
             elment =  blockingQueue.poll(2L,TimeUnit.SECONDS);
//             elment =  blockingQueue.poll();
            if(null != elment){
                System.out.println(Thread.currentThread().getName()+"\t"+"弹出队列成功,弹出"+elment);
            }else{
                System.out.println("00000000000000");
                flag = false;
                return;
            }
        }

    }

    public  void stop(){
        this.flag = false;
    }


}


public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            System.out.println();
            System.out.println();
            try {
                shareSource.add();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"Prod").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            System.out.println();
            System.out.println();
            try{
                shareSource.delete();
            }catch (Exception e){
                e.printStackTrace();
            }

        },"Consumer").start();






        try{
            TimeUnit.SECONDS.sleep(10);
            System.out.println("10s 后结束生产消费。。。。。。");
        }catch (Exception e){e.printStackTrace();}

        shareSource.stop();
    }

}


