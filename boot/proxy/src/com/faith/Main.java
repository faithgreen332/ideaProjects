package com.faith;

import com.faith.impl.MyServiceImpl;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        MyService service = new MyServiceImpl();

        MyService o = (MyService) (Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new MyInvokerHandler(service)));
        o.test1();
        o.test2("Faith");
    }
}
