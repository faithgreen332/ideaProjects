package com.faith.javabasic.condition;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();
        final Random rm = new Random();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 开始生产。。。");
                for (int i = 0; i < 20; i++) {
                    boundedBuffer.put(rm.nextInt(20));
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2 开始消费");
                for (int i = 0; i < 20; i++) {
                    Object o = boundedBuffer.take();
                    System.out.println(o);
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }


    static class BoundedBuffer {


        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();


        Object[] items = new Object[20];
        int count, putptr, takeptr;

        public void put(int nextInt) {
            System.out.println("put wait lock ...");
            lock.lock();
            System.out.println("put get lock");
            try {
                while (count == items.length) {
                    System.out.println("buffer full, please take");
                    notFull.await();
                }
                items[putptr] = nextInt;
                if (++putptr == items.length)
                    putptr = 0;
                ++count;
                notEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public Object take() {
            System.out.println("take wait lock");
            lock.lock();
            System.out.println("take get lock");
            try {
                while (count == 0) {
                    System.out.println("no elements, please put");
                    notEmpty.await();
                }
                Object item = items[takeptr];
                if (++takeptr == items.length)
                    takeptr = 0;
                --count;
                notFull.signal();
                return item;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return null;
        }
    }
}
