package com.ymy.boot.singleton;

/**
 * 多线程下单例模式的解决办法
 * DCL: Double Check Lock
 *
 * @author Ringo
 * @since 2021/4/13 17:19
 */
public class MultiThreadSingleton {

    // DCL中添加 volatile 就是为了禁止指令重排
    private static volatile MultiThreadSingleton instance = null;

    private MultiThreadSingleton() {
        System.out.println(Thread.currentThread().getName() + "\t 执行构造方法...");
    }

    /**
     * DCL: Double Check Lock 双端检锁机制
     * DCL 机制不一定线程安全, 原因是有指令重排的存在, 加入 volatile 可以禁止指令重排
     *
     * @author Ringo
     * @date 2021/4/13
     */
    public static MultiThreadSingleton getInstance() {
        if (instance == null) {
            synchronized (MultiThreadSingleton.class) {
                if (instance == null) {
                    instance = new MultiThreadSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                MultiThreadSingleton.getInstance();
            }).start();
        }
    }
}