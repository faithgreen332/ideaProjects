package com.faith.reactor;

/**
 * 1.创建 IO Thread ，一个或多个
 * 2.把监听的server注册到某个selector上
 * 3.
 */
public class Main {

    public static void main(String[] args) {

        SelectorThreadGroup stg = new SelectorThreadGroup(3);
//        SelectorThreadGroup stg = new SelectorThreadGroup(3); // 混杂模式，只有一个线程负责 accept ，其他会被分配client，进行 r/w

        stg.bind(9090);
    }

}
