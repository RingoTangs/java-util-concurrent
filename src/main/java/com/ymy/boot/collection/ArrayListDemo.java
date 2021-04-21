package com.ymy.boot.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程下: 集合类不安全
 * java.util.ConcurrentModificationException
 *
 * @author Ringo
 * @since 2021/4/14 11:04
 */
public class ArrayListDemo {

    public static void main(String[] args) throws Exception {
        testCapacity();
    }

    /**
     * 关于 ArrayList 初始化容量为 0，添加第1个元素之后容量为 10 的测试
     *
     * @author Ringo
     * @date 2021/4/14
     */
    public static void testCapacity() throws Exception {
        ArrayList<String> arrayList = new ArrayList<>();
        // 尝试放开注释再运行一遍
//        arrayList.add("1");
        Field elementDataField = arrayList.getClass().getDeclaredField("elementData");
        elementDataField.setAccessible(true);
        Object[] elementData = (Object[]) (elementDataField.get(arrayList));
        System.out.println("获得ArrayList的容量: " + elementData.length);
    }
}
