package com.ymy.boot.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ringo
 * @date 2021/4/19 22:56
 */
public class AQSDemo {

    public static void main(String[] args) {
        Bank bank = new Bank();
        new Thread(() -> bank.doService(), "t2").start();
        new Thread(() -> bank.doService(), "t3").start();
    }

    // 资源类
    private static class Bank {

        private ReentrantLock lock = new ReentrantLock();

        public void doService() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t thread come in...");
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "\t thread come out...");
                lock.unlock();
            }
        }
    }
}
