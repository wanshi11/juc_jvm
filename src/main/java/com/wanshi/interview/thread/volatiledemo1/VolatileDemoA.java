package com.wanshi.interview.thread.volatiledemo1;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.concurrent.TimeUnit;

/*
1 验证volatile的可见性
    1.1 加入int number=0，number变量之前根本没有添加volatile关键字修饰,没有可见性
    1.2 添加了volatile，可以解决可见性问题

int number = 0; 主线程会一直等待
volatile int number = 0; 主线程得到继续执行
*/
public class VolatileDemoA {


    public static void main(String[] args) {
        seeOkByVolatile();
    }

    private static void seeOkByVolatile() {
        MyDataA dataA = new MyDataA();

        //第一个线程操作
        new Thread(() -> {
            try{
                //先睡一下，要后启动
                TimeUnit.SECONDS.sleep(3);
            }catch (Exception e){
                e.printStackTrace();
            }
            dataA.addTo60();
            System.out.println(Thread.currentThread().getName()+ " \t updated number value: " +dataA.number);
        }).start();

        //main线程模拟第二个线程[当不等于0,跳出循环]
        while (dataA.number == 0){}
        System.out.println("已跳出while循环，，number value:"+dataA.number);
    }


}

//共享资源类
class MyDataA{

      int number;
//      volatile  int number;

      public void addTo60(){
          this.number = 60;
      }
}
