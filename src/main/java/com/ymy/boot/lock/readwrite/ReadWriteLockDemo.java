package com.ymy.boot.lock.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author Ringo
 * @since 2021/4/15 20:10
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Cache cache = new Cache();

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                cache.put("k" + temp, "v" + temp);
            }, "t" + i).start();
        }

        for (int i = 6; i <= 10; i++) {
            final int temp = i - 5;
            new Thread(() -> {
                cache.get("k" + temp);
            }, "t" + i).start();
        }
    }
}

// 资源类
class Cache {

    // volatile 保证线程间的可见性
    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入: " + key);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写入完成: " + key);
        } finally {
            writeLock.unlock();
        }
    }

    public Object get(String key) {
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读: " + key);
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t读取完成: " + value);

            // try语句块 有 return 最后也会执行 finally
            return value;
        } finally {
            readLock.unlock();
        }
    }
}
