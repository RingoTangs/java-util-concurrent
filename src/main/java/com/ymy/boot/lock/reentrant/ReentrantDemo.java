package com.ymy.boot.lock.reentrant;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 (递归锁)
 * 作用: 防止发生死锁
 *
 * @author Ringo
 * @since 2021/4/15 16:49
 */
public class ReentrantDemo {

    public static void main(String[] args) {
        // 1: 测试 synchronized 可重入
        Phone1 phone1 = new Phone1();
        new Thread(() -> {
            phone1.sendSms();
        }, "t1").start();
        new Thread(() -> {
            phone1.sendSms();
        }, "t2").start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ");

        // 2: 测试 ReentrantLock 可重入
        Phone2 phone2 = new Phone2();
        new Thread(() -> {
            phone2.sendSms();
        }, "t3").start();
        new Thread(() -> {
            phone2.sendSms();
        }, "t4").start();
    }

}

class Phone1 {

    // synchronized 锁的是当前对象
    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "\t sendSms()");
        this.sendEmail();
    }

    // synchronized 锁的是当前对象
    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t sendEmail()");
    }
}

class Phone2 {
    Lock lock = new ReentrantLock();

    public void sendSms() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t sendSms()");
            this.sendEmail();
        } finally {
            // lock() 和 unlock() 必须要成对出现
            lock.unlock();
            lock.unlock();
        }
    }

    public void sendEmail() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t sendEmail()");
        } finally {
            lock.unlock();
        }
    }
}
