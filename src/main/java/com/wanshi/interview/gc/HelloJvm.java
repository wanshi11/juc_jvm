package com.wanshi.interview.gc;

import java.util.concurrent.TimeUnit;

public class HelloJvm {

    //

    /**
     *  jinfo -flag 配置项 进程编号   ##查看jvm配置
     *  jinfo -flags 进程号
     *
     *
     *  -Xms   等价于 -XX:InitialHeapSize
     *  -Xmx   等价于 -XX:MaxHeapSize
     *
     *
     *  java -XX:+PrintFlagsInitial -version  查看jvm初始值
     *
     *  java -XX:+PrintCommandLineFlags -version   查看JVM修改值
     *  java -XX:+PrintFlagsFinal -version
     */
    public static void main(String[] args) {


        System.out.println("********hello JVM**********");
        try{TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);}catch (Exception e){e.printStackTrace();}
    }
}
