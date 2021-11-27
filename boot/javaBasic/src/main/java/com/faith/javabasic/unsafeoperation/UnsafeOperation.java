package com.faith.javabasic.unsafeoperation;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeOperation {
    private static Unsafe unsafe;
    Person person = null;
    private long offsetAge = 0;
    private long offsetName;

    public UnsafeOperation(Person person) {
        this.person = person;

        try {
            long person1 = unsafe.objectFieldOffset(UnsafeOperation.class.getDeclaredField("person"));
            System.out.println("person: " + person1 + " " + person);
            offsetAge = unsafe.objectFieldOffset(person.getClass().getDeclaredField("age"));
            System.out.println("offsetAge: " + offsetAge + " " + person.getAge());
            offsetAge = unsafe.objectFieldOffset(person.getClass().getDeclaredField("name"));
            System.out.println("offsetName: " + offsetName + " " + person.getName());

            Person p = new Person("faithgreen", 40);
            unsafe.compareAndSwapObject(this, person1, person, p);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        UnsafeOperation unsafeOperation = new UnsafeOperation(new Person("asdfasdf", 30));
        Person p = unsafeOperation.person;
        System.out.println(unsafeOperation.person.getName());

        try {
            System.out.println("offsetAge: " + unsafe.objectFieldOffset(p.getClass().getDeclaredField("age")) + " " + p.getAge());
            System.out.println("offsetName: " + unsafe.objectFieldOffset(p.getClass().getDeclaredField("name")) + " " + p.getName());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
