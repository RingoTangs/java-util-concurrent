package com.ymy.boot.collection;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Ringo
 * @since 2021/4/14 22:50
 */
public class HashMapDemo extends HashMap<String, Object> {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) throws Exception {
        // 1：测试2的幂
        System.out.println(powerOfTwo(1)); // 1
        System.out.println(powerOfTwo(13)); // 16
        System.out.println(powerOfTwo(22)); // 32

        // 2: 测试 HashMap 插入值和不插入值时数组的容量
        hashMapCapacity();

        // 3: 测试 HashMap 只有 key 相同 value 被覆盖的时候才会返回值
        // 第一次添加返回的都是 null
        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println(hashMap.put("1", 1)); // null
        System.out.println(hashMap.put("1", 2)); // 1
        System.out.println(hashMap.put("2", 1)); // null
    }

    /**
     * 根据传入的容量获得与它最相近的2的幂
     *
     * @param capacity 容量
     * @author Ringo
     * @date 2021/4/14
     */
    public static int powerOfTwo(int capacity) {
        int n = capacity - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * HashMap 插入值和不插入值时数组的容量
     *
     * @author Ringo
     * @date 2021/4/14
     */
    public static void hashMapCapacity() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>(7);
        hashMap.put("1", 1);
        Field tableField = hashMap.getClass().getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(hashMap);
        if (table == null) {
            System.out.println("null");
            return;
        }
        System.out.println("散列表的长度: " + table.length);
    }
}
