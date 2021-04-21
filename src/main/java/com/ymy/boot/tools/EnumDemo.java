package com.ymy.boot.tools;

import java.util.concurrent.CountDownLatch;

/**
 * 枚举厉害！
 * Enum、CountDownLatch 配合使用
 *
 * @author Ringo
 * @since 2021/4/15 21:11
 */
public class EnumDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "被灭！");
                countDownLatch.countDown();
            }, Country.get(i).getName() + i).start();
        }

        countDownLatch.await();

        System.out.println(Country.SEVEN.getName() + "统一天下！");
    }
}

/**
 * 1、枚举中不要写 set 方法
 * 2、枚举中可以写 属性 和 构造器
 * 3、枚举中需要写 get 方法
 *
 * @author Ringo
 * @date 2021/4/15
 */
enum Country {

    ONE(1, "齐国"),

    TWO(2, "楚国"),

    THREE(3, "燕国"),

    FOUR(4, "韩国"),

    FIVE(5, "赵国"),

    SIX(6, "魏国"),

    SEVEN(7, "秦国");

    private int index;

    private String name;

    Country(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static Country get(int index) {
        for (Country c : Country.values()) {
            if (c.index == index)
                return c;
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
