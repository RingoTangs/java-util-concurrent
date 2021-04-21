package com.ymy.boot.aqs;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 参照 {@link java.util.concurrent.locks.LockSupport}
 * 手写 FIFO 非可重入锁（自旋锁）
 *
 * @author Ringo
 * @date 2021/4/19 11:22
 */
public class NonReentrantLock {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    /**
     * 加锁
     *
     * @author Ringo
     * @date 2021/4/19
     */
    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // Block while not first in queue or cannot acquire lock
        // 1. not first in queue: 排队还没有轮到 current thread
        // 2. cannot acquire lock: 前一个 thread 还没有释放锁
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if (Thread.interrupted())
                wasInterrupted = true;
        }

        waiters.remove();

        if (wasInterrupted)
            current.interrupt();
    }

    /**
     * 解锁
     *
     * @author Ringo
     * @date 2021/4/19
     */
    public void unlock() {
        // 1: 解锁的时候, 一个线程用完之后才解锁, 不存在竞争关系
        locked.set(false);

        // 2: 队头线程解除阻塞
        LockSupport.unpark(waiters.peek());
    }
}
