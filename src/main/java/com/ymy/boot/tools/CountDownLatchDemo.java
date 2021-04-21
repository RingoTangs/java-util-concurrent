package com.ymy.boot.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch
 * 功能: 5 4 3 2 1 0 ok 发射！
 *
 * @author Ringo
 * @since 2021/4/15 20:50
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t离开教室");

                countDownLatch.countDown(); // 计数器 -1
            }, "student" + i).start();
        }

        // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        countDownLatch.await(3, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "\t 班长关门！");
    }
}
