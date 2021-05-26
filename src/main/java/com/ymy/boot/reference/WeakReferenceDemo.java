package com.ymy.boot.reference;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ringo
 * @date 2021/5/26 14:26
 */
public class WeakReferenceDemo {
    public static void main(String[] args) throws Exception {
//        WeakReference<M> wf = new WeakReference<>(new M());
////        System.out.println(wf.get());
////        System.gc();
////        Thread.sleep(500);
////        System.out.println(wf.get());
        M m = new M();
        WeakReference<M> wr = new WeakReference<>(m);
        Set<WeakReference<M>> set = new HashSet<>();
        set.add(wr);
        m = null;
        System.gc();
        Thread.sleep(500);
        set.forEach(s -> System.out.println(s.get()));
    }
}
