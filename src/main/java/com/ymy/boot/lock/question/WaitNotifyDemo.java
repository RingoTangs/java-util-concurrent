package com.ymy.boot.lock.question;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目: 两个线程, 可以操作初始值为 0 的一个变量,
 * 实现一个线程对该变量加1, 一个线程对该变量减1,
 * 实现交替, 来10轮, 最后变量初始值为 0.
 *
 * @author Ringo
 * @date 2021/4/16 22:38
 */
public class WaitNotifyDemo {

    public static void main(String[] args) {
        Resource resource = new Resource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    resource.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    resource.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

// 资源类
class Resource {

    private int var = 0;

    public synchronized void increment() throws Exception {
        while (var != 0) {
            this.wait();
        }
        ++var;
        System.out.println(var);
        this.notify();
    }

    public synchronized void decrement() throws Exception {
        while (var == 0) {
            this.wait();
        }
        --var;
        System.out.println(var);
        this.notify();
    }
}
