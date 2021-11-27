package com.faith;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvokerHandler implements InvocationHandler {

    private MyService target;

    public MyInvokerHandler(MyService target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(target, args);
        if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        }
        return invoke + "proxy";
    }
}
