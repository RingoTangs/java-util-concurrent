package com.ymy.boot.thread;

import java.util.concurrent.*;

/**
 * 实现 {@link Callable} 创建线程
 *
 * @author Ringo
 * @date 2021/4/17 11:43
 */
public class CallableDemo {
    public static void main(String[] args) {
        // 1: 创建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(1,
                Runtime.getRuntime().availableProcessors(),
                2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 2: 执行任务
        try {
            for (int i = 1; i <= 50; i++) {
                try {
                    // Future 保存 Callable任务的执行结果
                    Future<String> future = threadPool.submit(new CallableTask());
                    System.out.println(future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            threadPool.shutdown();
        }
    }
}

// 任务实现 Callable 接口
class CallableTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName() + "\tHello";
    }
}

