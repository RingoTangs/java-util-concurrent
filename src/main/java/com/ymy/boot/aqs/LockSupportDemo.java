package com.ymy.boot.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport:
 * 1. 只阻塞和唤醒线程, 不用先拿到锁
 * 2. park(): 阻塞线程; unPark(): 解除线程阻塞
 * 3. park() unPark() 没有先后顺序
 *
 * @author Ringo
 * @date 2021/4/19 16:59
 */
public class LockSupportDemo {

    public static void main(String[] args) {

        String a = "123";

        Thread t1 = new Thread(() -> {
            /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println(Thread.currentThread().getName() + "\t park...");
            LockSupport.park(a);
            System.out.println("a: " + a);
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t unPark...");
        }, "t2");

        t2.start();
    }
}
