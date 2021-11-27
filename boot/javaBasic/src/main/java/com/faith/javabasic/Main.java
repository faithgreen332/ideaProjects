package com.faith.javabasic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
//        Map<String, String> map = new LinkedHashMap<>(16, 12, true);
//        map.put("a", "faith");
//        map.put("b", "green");
//        map.put("a", "ffffff");

//        LurCache<String, String> lruCache = new LurCache<>(4);
//        lruCache.put("a", "faith");
//        lruCache.put("b", "green");
//        lruCache.put("c", "red");
//        lruCache.put("d", "yellow");
//        lruCache.put("e", "white");
//        lruCache.get("c");

        List<String> list = new CopyOnWriteArrayList<>();

        Thread[] threads = new Thread[3];
//        synchronized (threads) {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread("T" + i) {
                    @Override
                    public void run() {
                        list.add(Thread.currentThread().getName());
                    }
                };
            }
            for (Thread r : threads) {
                r.start();
            }

            list.forEach(System.out::println);
//        }
    }
}
