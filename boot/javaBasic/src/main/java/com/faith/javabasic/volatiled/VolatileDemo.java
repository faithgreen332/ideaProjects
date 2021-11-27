package com.faith.javabasic.volatiled;

public class VolatileDemo {

    public static void main(String[] args) {

        Task task = new Task();

        for (int i = 0; i < 100; i++) {
            new Thread(task, "第" + i + "个线程：").start();
        }

//        System.out.println(task.getCount());

    }

    static class Task implements Runnable {

        private  int count;

        @Override
        public void run() {
            synchronized (Task.class){
                for (int i = 0; i < 10000; i++) {
                    count++;
                    System.out.println(count);
                }
            }

        }

        public int getCount() {
            return count;
        }
    }
}
