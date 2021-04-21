package com.ymy.boot.singleton;

/**
 * 多线程下的单例模式会出错
 *
 * @author Ringo
 * @since 2021/4/13 17:04
 */
public class SingletonMode {
    private static SingletonMode instance = null;

    private SingletonMode() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法..");
    }

    public static SingletonMode getInstance() {
        if (instance == null) {
            instance = new SingletonMode();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingletonMode.getInstance();
            }).start();
        }
    }
}
