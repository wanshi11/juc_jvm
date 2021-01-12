package com.wanshi.interview.thread.pool1;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;

import java.util.concurrent.*;

public class CallableDemo{


    public static void main(String[] args) throws  Exception {
//        第二种方式
//        MyThead myThead = new MyThead();
//        FutureTask<Integer> futureTask = new FutureTask<>(myThead);
//        Thread t1 = new Thread(futureTask,"AAAA");
//        t1.start();


        //第一种方式
        ExecutorService executor = Executors.newCachedThreadPool();
        MyThead myThead = new MyThead();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(myThead);
        executor.submit(futureTask);


        //executor.execute(futureTask);
        executor.shutdown();

        int r1 = 1024;
        int r2 ;


        //
        while (!futureTask.isDone()){
            System.out.println("futureTask.get() :::::"+futureTask.get());
             r2 = futureTask.get(); //要求获得Callable线程的计算结果，如果没有计算完成就要强求，会导致阻塞，知道计算完成，建议放在最后

            Integer reValue = r1+ r2;
            System.out.println("================="+reValue);
        }



    }

}

class  MyThead implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("come in call()........");
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){e.printStackTrace();}
        return 1024;
    }
}
