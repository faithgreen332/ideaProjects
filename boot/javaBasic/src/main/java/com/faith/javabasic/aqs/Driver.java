package com.faith.javabasic.aqs;

import java.util.concurrent.CountDownLatch;

public class Driver {

    static int N = 3;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        System.out.println("车手正在准备");
        startSignal.countDown();      // let all threads proceed
        System.out.println("车手都准备好了");            // don't let run yet
        doneSignal.await();           // wait for all to finish
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        } // return;
    }

    void doWork() {
        System.out.println("车手" + Thread.currentThread().getName() + "开始跑。。。");
    }
}
