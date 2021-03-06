package com.faith.javabasic;

import com.faith.javabasic.lru.LurCache;
import com.faith.javabasic.readwritelock.ReentrantWriteReadLockDemo;

import java.util.concurrent.atomic.AtomicInteger;

public class TestMain {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    private static final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    public static void main(String[] args) {
        final ReentrantWriteReadLockDemo writeReadLockDemo = new ReentrantWriteReadLockDemo();

//        Thread thread = new Thread(() -> {
////            writeReadLockDemo.write();
//            writeReadLockDemo.read();
//        }, "t1");
//        thread.start();

//        float matrix[][] = {{2, 3}, {4, 5}, {6, 7}};
//        System.out.println(matrix.length);

        int c = TestMain.ctl.get();
        System.out.println(workerCountOf(c));
        System.out.println(runStateOf(c));


    }
    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
}
