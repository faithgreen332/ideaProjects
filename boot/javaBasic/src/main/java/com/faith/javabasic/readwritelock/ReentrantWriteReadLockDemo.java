package com.faith.javabasic.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantWriteReadLockDemo {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void read() {
        try {
            readLock.lock();
            System.out.println("线程" + Thread.currentThread().getName() + "进入");
            try {
                Thread.sleep(1000);
                write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println("线程" + Thread.currentThread().getName() + "进入");
            try {
                Thread.sleep(1000);
                read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            writeLock.unlock();
        }
    }
}
