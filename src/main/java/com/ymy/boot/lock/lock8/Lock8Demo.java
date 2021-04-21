package com.ymy.boot.lock.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 8锁案例:
 * <li>
 * 1、标准访问, 同一部手机, 先打印邮件还是短信？
 * 对象锁, 两个方法用的一把锁
 * </li>
 *
 * <li>
 * 2、邮件方法暂停 4s, 同一部手机, 先打印邮件还是短信？
 * 对象锁, 两个方法用的一把锁
 * </li>
 *
 * <li>
 * 3、新增普通的 sayHello() 方法, 同一部手机, 先打印邮件还是 hello？
 * sendEmail()需要对象锁;
 * sayHello() 不需要获取锁
 * </li>
 *
 * <li>
 * 4、两部手机, 先打印邮件还是短信?
 * sendEmail() 锁的是实例 phone1;
 * sendEmail() 锁的是实例 phone2
 * 两把锁, 互不影响
 * </li>
 *
 * <li>
 * 5、两个静态同步方法, 同一部手机, 先打印邮件还是短信？
 * static 类锁, 同一把锁
 * </li>
 *
 * <li>
 * 6、两个静态同步方法, 两部手机, 先打印邮件还是短信？
 * static 类锁, 同一把锁
 * </li>
 *
 * <li>
 * 7、1个静态同步方法, 1个普通同步方法, 同一部手机, 先打印邮件还是短信？
 * static 类锁
 * 普通的同步方法 对象锁
 * 两把锁, 互不影响
 * </li>
 *
 * <li>
 * 8、1个静态同步方法, 1个普通同步方法, 2部手机, 先打印邮件还是短信？
 * static 类锁
 * 普通的同步方法 锁当前对象
 * 两把锁, 互不影响
 * </li>
 *
 * @author Ringo
 * @date 2021/4/16 19:16
 */
public class Lock8Demo {
    public static void main(String[] args) throws Exception {
        // 资源
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        // 线程 1
        new Thread(() -> {
            phone1.sendEmail();
        }, "t1").start();

        Thread.sleep(1000);

        // 线程 2
        new Thread(() -> {
//            phone1.sendSms();
//            phone1.sayHello();
            phone2.sendSms();
        }, "t2").start();
    }
}

// 资源类
//  new Thread(()->{}, "t1").start();
class Phone {
    // 1: lock1
//    public synchronized void sendEmail() {
//        System.out.println("### send email...");
//    }
//
//    public synchronized void sendSms() {
//        System.out.println("*** send sms...");
//    }

    // 2: lock2 lock4
//    public synchronized void sendEmail() {
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("### send email...");
//    }
//
//    public synchronized void sendSms() {
//        System.out.println("*** send sms...");
//    }

    // 3: lock3
//    public synchronized void sendEmail() {
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("### send email...");
//    }
//
//    public void sayHello() {
//        System.out.println("hello..");
//    }

    // lock5 lock6
//    public static synchronized void sendEmail() {
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("### send email...");
//    }
//
//    public static synchronized void sendSms() {
//        System.out.println("*** send sms...");
//    }

    // lock 7 lock 8
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("### send email...");
    }

    public static synchronized void sendSms() {
        System.out.println("*** send sms...");
    }
}
