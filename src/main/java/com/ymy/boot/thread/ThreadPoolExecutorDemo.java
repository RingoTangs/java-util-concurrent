package com.ymy.boot.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池: Executors
 * 注意：Executors 中提供的三大方法我们都不能用，阻塞队列长度都是 Integer.MAX_SIZE
 *
 * @author Ringo
 * @date 2021/4/17 13:12
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
//        fixThreadPool();
//        singleThreadPool();
        cacheThreadPool();
    }

    /**
     * 1: 一池固定线程
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void fixThreadPool() {
        // 1: 一池5个工作线程(相当于银行5个服务窗口)
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 2: 模拟有 10个 顾客来银行办理业务
        work(threadPool);
    }

    /**
     * 2: 一池一线程
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void singleThreadPool() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        work(threadPool);
    }

    /**
     * 3: 一池 N 线程(可根据业务自动扩容)
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void cacheThreadPool() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        work(threadPool);
    }

    /**
     * 模拟线程池办理业务
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void work(ExecutorService threadPool) {
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }
    }
}
