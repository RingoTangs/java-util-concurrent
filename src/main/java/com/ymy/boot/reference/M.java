package com.ymy.boot.reference;

/**
 * @author Ringo
 * @date 2021/5/26 12:59
 */
public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize..");
        super.finalize();
    }
}
