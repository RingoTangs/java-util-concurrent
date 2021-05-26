package com.ymy.boot.reference;

/**
 * @author Ringo
 * @date 2021/5/26 13:04
 */
public class NormalReferenceDemo {
    public static void main(String[] args) throws Exception {
        M m = new M();
        m = null;
        System.gc();
        System.in.read();
    }
}
