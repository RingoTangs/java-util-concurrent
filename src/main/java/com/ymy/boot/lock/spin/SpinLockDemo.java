package com.ymy.boot.lock.spin;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 *
 * @author Ringo
 * @since 2021/4/15 19:42
 */
public class SpinLockDemo {

    // 原子引用, 泛型 Thread
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 自旋锁 加锁
    public void lock() {
        Thread currentThread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, currentThread)) ;
        System.out.println(currentThread.getName() + "\t lock()");
    }

    // 解锁
    public void unlock() {
        Thread currentThread = Thread.currentThread();
        atomicReference.compareAndSet(currentThread, null);
        System.out.println(currentThread.getName() + "\t come in unlock()");
    }

    public static void main(String[] args) throws Exception {
        SpinLockDemo spinLock = new SpinLockDemo();
        new Thread(() -> {
            spinLock.lock();
            spinLock.unlock();
        }, "t1").start();

        Thread.sleep(1000);

        new Thread(() -> {
            spinLock.lock();
            spinLock.unlock();
        }, "t2").start();
    }
}
