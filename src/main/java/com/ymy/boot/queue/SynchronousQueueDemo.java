package com.ymy.boot.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue:  没有容量，它不存储任何元素
 * 每一个 put 操作必须要等待一个 take 操作，否则不能继续添加元素
 *
 * @author Ringo
 * @date 2021/4/16 19:03
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {

        // 1: 同步阻塞队列
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        // 2: 线程 t1
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "\tput\t" + i);
                    blockingQueue.put(i + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        // 3: 线程 t2
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println(blockingQueue.take());
                Thread.sleep(5000);
                System.out.println(blockingQueue.take());
                Thread.sleep(5000);
                System.out.println(blockingQueue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
