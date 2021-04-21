package com.ymy.boot.aqs;

/**
 * synchronized: wait-notify限制
 * 1. wait-notify 必须配合 synchronized 一起使用。
 * 2. wait在前 notify 在后 Thread 才可以被唤醒。
 *
 * @author Ringo
 * @date 2021/4/19 16:11
 */
public class WaitNotifyRestrictDemo {

    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            resource.methodWait();
        }, "t1").start();

        new Thread(() -> {
            resource.methodNotify();
        }, "t2").start();
    }

    private static class Resource {
        public synchronized void methodWait() {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t wait");
        }

        public synchronized void methodNotify() {
            System.out.println(Thread.currentThread().getName() + "\t notify");
            this.notify();
        }
    }
}
