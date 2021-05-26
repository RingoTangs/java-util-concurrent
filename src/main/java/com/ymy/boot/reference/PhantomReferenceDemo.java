package com.ymy.boot.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用的作用: 管理直接内存。
 *
 * @author Ringo
 * @date 2021/5/26 14:34
 */
public class PhantomReferenceDemo {
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) {
        PhantomReference<M> pr = new PhantomReference<>(new M(), QUEUE);
        System.out.println(pr.get());              // null 根本拿不到, 回不回收都拿不到
    }
}
