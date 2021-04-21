package com.ymy.boot.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 带有时间戳的原子引用: 可以解决 ABA 问题
 *
 * @author Ringo
 * @since 2021/4/13 20:14
 */
public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomic
                = new AtomicStampedReference<>(new Integer(1), 0);
        Integer val = atomic.getReference();
        int stamp = atomic.getStamp();
        atomic.compareAndSet(val, 99, stamp, stamp + 1);
    }
}
