package com.ymy.boot.cas;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用：封装对象
 *
 * @author Ringo
 * @since 2021/4/13 19:32
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        Person person1 = new Person("zs", 18);
        Person person2 = new Person("li4", 20);

        AtomicReference<Person> atomicReference = new AtomicReference<>();
        atomicReference.set(person1);

        System.out.println(atomicReference.compareAndSet(person1, person2) + "\t" + atomicReference.get());
        System.out.println(atomicReference.compareAndSet(person1, person2) + "\t" + atomicReference.get());
    }
}


class Person {
    public String name;
    public int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
