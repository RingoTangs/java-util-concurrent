package com.ymy.boot.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁代码
 *
 * @author Ringo
 * @date 2021/4/18 19:21
 */
public class DeadLockDemo1 {
    public static void main(String[] args) {
        String a = "aaa";
        String b = "bbb"; // 如果 b = "aaa" 因为是字符串缓冲区 a和b指向同一对象, 同一把锁

        String c = new String("ccc");
        String d = new String("ccc");

        new Thread(() -> {
            synchronized (c) {
                System.out.println(Thread.currentThread().getName() + "\t获取" + a);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (d) {
                    System.out.println(Thread.currentThread().getName() + "\t获取" + b);
                }
            }
        }, "Thread A").start();

        new Thread(() -> {
            synchronized (d) {
                System.out.println(Thread.currentThread().getName() + "\t获取" + b);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (c) {
                    System.out.println(Thread.currentThread().getName() + "\t获取" + a);
                }
            }
        }, "Thread B").start();
    }
}
