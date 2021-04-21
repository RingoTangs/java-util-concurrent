package com.ymy.boot;

/**
 * 中断: interrupt
 *
 * @author Ringo
 * @date 2021/4/19 13:21
 */
public class InterruptDemo {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    // 1: 中断失败：只是发出中断信号, 但是没有接收中断信号
    public static void test1() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
            }
        });
        thread.start();
        thread.interrupt();
    }

    // 2: 中断成功
    public static void test2() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();

                // 响应中断
                // 或者 if (Thread.interrupted())
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("响应中断, 程序退出...");
                    return;
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    // 3: blocked 状态被中断会清除中断状态
    public static void test3() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // 已经清除中断状态了
                System.out.println("sleep时被中断..");
                // 手动发起中断信号
                Thread.currentThread().interrupt();
            }

            while (true) {
                Thread.yield();
                // 响应中断
                // 或者 if (Thread.interrupted())
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("响应中断, 程序退出...");
                    return;
                }
            }
        });
        thread.start();
        thread.interrupt();
    }
}
