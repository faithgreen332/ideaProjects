package com.faith.javabasic.pool;

import java.util.concurrent.*;

public class ThreadPoolTest {

    private static ExecutorService pool = null;

    public static void main(String[] args) {
        pool = new ThreadPoolExecutor(2, 3, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "线程：" + r.hashCode());

                    }
                }, new ThreadPoolExecutor.AbortPolicy()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + ((RunTask) r).getTaskName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行结束：" + ((RunTask) r).getTaskName());
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                super.terminated();
            }
        };

        for (int i = 0; i < 6; i++) {
            pool.execute(new RunTask("task-" + i));
        }
    }

    private static class RunTask implements Runnable {

        private String taskName;

        public RunTask(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "正在执行");
        }
    }
}
