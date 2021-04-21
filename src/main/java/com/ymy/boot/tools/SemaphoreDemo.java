package com.ymy.boot.tools;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Ringo
 * @date 2021/4/16 12:06
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("==== " + Thread.currentThread().getName() + "来到停车场");
                    if (semaphore.availablePermits() == 0) {
                        System.out.println("对" + Thread.currentThread().getName() + "说: 车位不足，请耐心等待");
                    }
                    semaphore.acquire(1);
                    System.out.println(Thread.currentThread().getName() + "成功进入停车场");
                    Thread.sleep(new Random().nextInt(10000));//模拟车辆在停车场停留的时间
                    System.out.println(Thread.currentThread().getName() + "驶出停车场");
                    semaphore.release();//释放令牌，腾出停车场车位
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i + "").start();
        }
    }
}
