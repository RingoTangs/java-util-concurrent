package com.ymy.boot.thread;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * CompletableFuture: 异步回调
 *
 * @author Ringo
 * @date 2021/4/17 23:06
 */
public class CompletableFutureDemo {


    public static void main(String[] args) throws Exception {
        // 1: 无返回值
//        runAsync();

        // 2: 有返回值
//        supplyAsync();

        // 3: 当异步任务完成时的回调
//        whenComplete();

        // 4: then
//        thenApply();

        handle();
    }

    /**
     * 1: 无返回值
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void runAsync() throws Exception {
        // 1: 创建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
                2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        // 2: 无返回值异步回调
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("runAsync()...");
            }, threadPool);

            System.out.println(future.get());
        } finally {
            threadPool.shutdown();
        }
    }


    /**
     * 2: 有返回值
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void supplyAsync() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10);
        System.out.println(future.get());
    }

    /**
     * 3: 计算结果完成时的回调
     *
     * @author Ringo
     * @date 2021/4/17
     */
    public static void whenComplete() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 0)
                // 异步任务无论成功还是异常都会来到这里
                // 成功 t != null; 抛异常 t == null
                .whenCompleteAsync((t, u) -> {  // t: 结果; u: 异常
                    if (t != null)
                        System.out.println("执行完成\t" + 10);
                })
                // 异步任务执行结果出现异常就会进入到这里
                .exceptionally(throwable -> {
                    System.out.println("执行出现了异常..." + throwable);
                    return 8848;
                });
        System.out.println(future.get());
    }

    /**
     * 4: thenApply()
     *
     * @author Ringo
     * @date 2021/4/18
     */
    public static void thenApply() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(t -> ++t)
                .whenCompleteAsync((t, u) -> {
                    if (t != null)
                        System.out.println("执行结果: " + t);
                }).exceptionally(exception -> 8848);
        System.out.println(future.get());
    }

    /**
     * 5、handle()
     *
     * @author Ringo
     * @date 2021/4/18
     */
    public static void handle() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(t -> ++t)
                .whenCompleteAsync((res, ex) -> {
                    if (res != null)
                        System.out.println("执行结果: " + res);
                })
                .handleAsync((res, ex) -> {
                    System.out.println(ex);
                    System.out.println(res);
                    if (ex != null) {
                        return 8848;
                    }
                    return res;
                });
        System.out.println(future.get());
    }
}
