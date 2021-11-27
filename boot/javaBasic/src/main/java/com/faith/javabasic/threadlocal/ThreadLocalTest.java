package com.faith.javabasic.threadlocal;

public class ThreadLocalTest {

    private static ThreadLocal<String> th = new ThreadLocal<>();

    public void test() {
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            assert threads != null;
            threads[i] = new Thread(() -> {
                th.set("thread: " + finalI);
                th.get();
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void main(String[] args) {
        new ThreadLocalTest().test();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(th);
    }
}
