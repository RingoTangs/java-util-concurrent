package com.ymy.boot.lock;

/**
 * @author Ringo
 * @date 2021/4/18 20:52
 */
public class SynchronizedDemo {

    // 1: 同步方法
    public synchronized void get() {
        System.out.println("Hello World");
    }

    // 2: 同步代码块
    public void set() {
        synchronized (this) {
            System.out.println("Hello World");
        }
    }
}
