package com.faith.rxjava;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.*;
import rx.functions.Action0;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RxjavaApplication {

    public static void main(String[] args) {
//        create_demo();
//        just_demo();
//        from_demo();
//        defer_demo();
        range_demo();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void range_demo() {
        Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new Worker() {
                    @Override
                    public Subscription schedule(Action0 action) {
//                        action.call();
                        return null;
                    }

                    @Override
                    public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
                        action.call();
                        return null;
                    }

                    @Override
                    public void unsubscribe() {

                    }

                    @Override
                    public boolean isUnsubscribed() {
                        return false;
                    }
                };
            }
        };
        Observable.range(1, 5, scheduler).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("on Completed ...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("on Error ...");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }
        });
    }

    private static void defer_demo() {
        String value = "hello world";
        Observable.defer(() -> Observable.just(value)).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("on Completed ...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("on Error ...");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });


    }

    private static void from_demo() {
        Observable.from(new String[]{"a", "b", "c"}).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("on Completed ...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("on Error ...");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }

    private static void just_demo() {
        Observable.just("a", "b", "c").subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("on Completed ...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("on Error ...");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }

    private static void create_demo() {
        Observable.create((Observable.OnSubscribe<String>) subscribe -> subscribe.onNext("RxJava 学习 ...")).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("on Completed ...");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("on Error");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }

}
