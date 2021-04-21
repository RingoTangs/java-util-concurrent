package com.ymy.boot.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * 测试样例中 静态内部类就是方便被实例化
 *
 * @author Ringo
 * @date 2021/4/18 12:51
 */
public class DeadLockDemo {

    public static void main(String[] args) {

        DeadLockDemo.Resource1 resource1 = new DeadLockDemo.Resource1();
        DeadLockDemo.Resource2 resource2 = new DeadLockDemo.Resource2();

        new Thread(() -> {
            resource1.get();
        }, "A").start();
        new Thread(() -> {
            resource2.get();
        }, "B").start();
    }

    private static class Resource1 {
        public void get() {
            // 1: 获取 Resource1 类锁
            synchronized (Resource1.class) {
                System.out.println(Thread.currentThread().getName() + "\t 获取 Res1");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 2: 尝试获取 Resource2 类锁
                synchronized (Resource2.class) {
                    System.out.println(Thread.currentThread().getName() + "\t 获取 Res2");
                }
            }
        }
    }

    private static class Resource2 {
        public void get() {
            synchronized (Resource2.class) {
                System.out.println(Thread.currentThread().getName() + "\t 获取 Res2");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (Resource1.class) {
                    System.out.println(Thread.currentThread().getName() + "\t 获取 Res1");
                }
            }
        }
    }
}
