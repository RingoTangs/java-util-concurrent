package com.ymy.boot;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试 volatile 原子性
 * 原子性:
 * 1、原子不可再分。
 * 2、线程在做某个具体业务时, 中间不可被其他线程干扰。
 * 3、整体完整性一致, 要么同时成功, 要么同时失败。
 *
 * @author Ringo
 * @since 2021/4/13 15:30
 */
public class VolatileAtomic {
    public static void main(String[] args) {
        // 1: 资源类
        Resource1 resource = new Resource1();

        // 2: 创建 20 个线程每个线程都执行 add() 方法 1w 次
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 10000; j++) {
                    resource.add();
                }
            }, i + "").start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield(); // main 线程让出 CPU
        }

        // 3: 当上面20个线程执行结束, main线程输出结果
        System.out.println("number: " + resource.number);
    }
}

/**
 * volatile 原子性解决方案：
 * 1、加锁: add()方法上添加synchronized
 * 2、不加锁: 使用 juc 下原子类 AtomicInteger
 *
 * @author Ringo
 * @date 2021/4/13
 */
class Resource1 {

    public AtomicInteger number = new AtomicInteger(0);

    public void add() {
        this.number.incrementAndGet();
    }
}
