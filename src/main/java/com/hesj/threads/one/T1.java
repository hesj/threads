package com.hesj.threads.one;

/**
 * 1、现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行?
 *
 * 这个线程问题通常会在第一轮或电话面试阶段被问到，目的是检测你对”join”方法是否熟悉。这个多线程问题比较简单，可以用join方法实现。
 */
public class T1 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            System.out.println("t1");
        });

        Thread t2 = new Thread(()->{
            System.out.println("t2");
        });

        Thread t3 = new Thread(()->{
            System.out.println("t3");
        });

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();

        System.out.println("finish");
    }
}
