package com.ymy.boot.collection;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 多线程下 HashSet 线程不安全
 *
 * @author Ringo
 * @since 2021/4/14 18:08
 */
public class HashSetDemo {

    public static void main(String[] args) throws Exception {
        // 1: CopyOnWriteArraySet
//        Set<Integer> set = new CopyOnWriteArraySet<>();
//        for (int i = 1; i <= 1000; i++) {
//            final int temp = i;
//            new Thread(() -> {
//                set.add(temp);
//                System.out.println(set);
//            }).start();
//        }

//        Map<String, Object> map = new HashMap<>();
//        System.out.println(map.put("1", 88));
//        System.out.println(map.put("1", 89));
//        System.out.println(map.put("1", 90));
//        System.out.println(map);
//
//        HashSet<Integer> set = new HashSet<>();
//        System.out.println(set.add(10));
//        System.out.println(set.add(10));

    }
}
