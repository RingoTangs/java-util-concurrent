package com.ymy.boot;

/**
 * 测试 volatile 的可见性
 *
 * @author Ringo
 * @since 2021/4/13 14:59
 */
public class VolatileVisibility {
    public static void main(String[] args) {
        // 1: 资源类
        Resource resource = new Resource();

        // 2: 创建 A 线程操作资源
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in...");
            // 暂停一会儿线程
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value: " + resource.number);
        }, "A").start();

        // 3: 第二个线程就是我们的 main 线程
        while (resource.number == 0) {
            // main线程就一直在这里循环, 直到 number 值不再是0
        }

        // 4: if main 线程感知到 number 已经被修改了, 则会跳出循环打印下面的语句！
        System.out.println(Thread.currentThread().getName() + "\t mission is over.");
    }
}

class Resource {
//    public int number = 0;

    public volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }
}
