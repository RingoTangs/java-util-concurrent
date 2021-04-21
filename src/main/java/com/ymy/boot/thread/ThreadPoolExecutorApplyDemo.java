package com.ymy.boot.thread;

import java.util.concurrent.*;

/**
 * 手写线程池
 *
 * @author Ringo
 * @date 2021/4/17 18:46
 */
public class ThreadPoolExecutorApplyDemo {
    public static void main(String[] args) throws Exception {

        // 1: 阻塞队列
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);

        // 2: 创建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(1,
                Runtime.getRuntime().availableProcessors(),
                2, TimeUnit.SECONDS,
                blockingQueue, Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        // 3: 执行并关闭
        try {
            for (int i = 1; i <= 50; i++) {
                final int temp = i;
                TimeUnit.MILLISECONDS.sleep(10);
                threadPool.submit(() -> {
                    System.out.println(temp + "\t 号被处理...");
                });
            }
        } finally {
            threadPool.shutdown();
        }
    }
}
