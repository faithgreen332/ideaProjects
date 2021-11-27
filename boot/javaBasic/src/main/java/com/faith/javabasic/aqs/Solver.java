package com.faith.javabasic.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Solver {

    final int N;
    final float[][] data;
    final CyclicBarrier barrier;
    float count;

    class Worker implements Runnable {
        int myRow;
        int rowCount;

        Worker(int row, int rowCount) {
            myRow = row;
            this.rowCount = rowCount;
        }

        public void run() {
            while (!(rowCount <= 0)) {
                processRow(myRow);

                try {
                    barrier.await();
                } catch (InterruptedException ex) {
                    return;
                } catch (BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private void processRow(int myRow) {
            float countRow = 0;
            for (float v : data[myRow]) {
                countRow += v;
            }
            rowCount--;
        }
    }

    public Solver(float[][] matrix) throws InterruptedException {
        data = matrix;
        N = matrix.length;
        Runnable barrierAction =
                new Runnable() {
                    public void run() {
//                        mergeRows(...);
                    }
                };
        barrier = new CyclicBarrier(N, barrierAction);

        List<Thread> threads = new ArrayList<Thread>(N);
        for (int i = 0; i < N; i++) {
            Thread thread = new Thread(new Worker(i, N));
            threads.add(thread);
            thread.start();
        }

        // wait until done
        for (Thread thread : threads)
            thread.join();
    }
}
