package com.ymy.boot.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition: await-signal 限制
 *
 * @author Ringo
 * @date 2021/4/19 16:32
 */
public class AwaitSignalRestrictDemo {

    public static void main(String[] args) {
        Resource resource = new Resource();

        new Thread(() -> resource.methodAwait(), "t1").start();

        new Thread(() -> resource.methodSignal(), "t2").start();
    }

    private static class Resource {
        private Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        public void methodAwait() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t await...");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t 被唤醒...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void methodSignal() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t signal...");
                condition.signal();
            } finally {
                lock.unlock();
            }

        }
    }

}
