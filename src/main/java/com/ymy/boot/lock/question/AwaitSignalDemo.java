package com.ymy.boot.lock.question;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实现精准通知
 * 题目: 多线程之间按顺序调用, 实现 A->B->C 三个线程启动, 要求如下:
 * "aa" 打印 5 次, "bb" 打印 10 次, "cc" 打印 15 次 循环 10 轮
 *
 * @author Ringo
 * @date 2021/4/16 23:29
 */
public class AwaitSignalDemo {

    public static void main(String[] args) throws Exception {
        Resource1 resource = new Resource1(3);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print(0, 1, 1, "aa");
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print(1, 2, 2, "bb");
            }
        }, "B").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print(2, 0, 3, "cc");
                System.out.println("第【" + i + "】轮结束！");
            }
        }, "C").start();
    }
}

// 资源类
class Resource1 {

    private ReentrantLock lock = new ReentrantLock();

    // 标志位
    // 0 => Thread A; 1 => Thread B; 2 => Thread C ...
    private int flag = 0;

    // 线程数量
    private int threadCount;

    // 条件
    private Condition[] conditions;

    // 构造方法
    public Resource1(int threadCount) {
        this.threadCount = threadCount;
        this.conditions = new AbstractQueuedSynchronizer.ConditionObject[this.threadCount];
        for (int i = 0; i < this.threadCount; i++) {
            this.conditions[i] = this.lock.newCondition();
        }
    }

    /**
     * @param index   选定执行打印的线程 (0: Thread A; 1: Thread B; 2: Thread C ....)
     * @param next    要通知的线程
     * @param n       打印的次数
     * @param content 打印的内容
     * @author Ringo
     * @date 2021/4/17
     */
    public void print(int index, int next, int n, String content) {
        if (index < 0 || index > this.threadCount || next < 0 || next > this.threadCount) {
            throw new RuntimeException("index/next的值 < 0 或者 > 最大线程数");
        }
        lock.lock();
        try {
            // 防止虚假唤醒
            while (flag != index) {
                this.conditions[index].await();
            }

            // 业务
            for (int i = 1; i <= n; i++) {
                System.out.println(content + i);
            }

            // 修改标志位
            flag = next;

            // 唤醒下一个线程
            this.conditions[flag].signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
