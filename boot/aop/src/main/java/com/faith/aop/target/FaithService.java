package com.faith.aop.target;

public class FaithService {
    @Override
    public String toString() {
        System.out.println("faithService toString invoke");
        return "faithService";
    }

    public void testAop() {
        System.out.println("testAop invoke");
    }
}
