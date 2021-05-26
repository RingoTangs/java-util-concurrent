package com.ymy.boot.reference;

import java.lang.ref.SoftReference;

/**
 * @author Ringo
 * @date 2021/5/26 13:35
 */
public class SoftReferenceDemo {
    public static void main(String[] args) throws Exception {
        SoftReference<byte[]> m =
                new SoftReference<>(new byte[1024 * 1024 * 10]);

        System.out.println(m.get());            // 拿到字节数组
        System.gc();
        Thread.sleep(500);
        System.out.println(m.get());            // GC之后拿到字节数组

        byte[] b = new byte[1024 * 1024 * 12];  // 堆里再分配15M空间, 显然堆中空间不够
        System.out.println(m.get());            // 拿不到软引用值了
    }
}
