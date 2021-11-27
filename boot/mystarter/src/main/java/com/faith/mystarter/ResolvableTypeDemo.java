package com.faith.mystarter;

import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ResolvableTypeDemo {

    public void test(List<String> list, Map<String, List<Integer>> map) {

    }

    public static void main(String[] args) {

        Class<ResolvableTypeDemo> resolvableTypeDemoClass = ResolvableTypeDemo.class;
        Method[] declaredMethods = resolvableTypeDemoClass.getDeclaredMethods();
        Method test = declaredMethods[1];
        // 获取方法的第一个参数对应的ResolvableType，参数为-1代表返回值，0为第一个，1为第二个，一次增加
        ResolvableType resolvableType0 = ResolvableType.forMethodParameter(test, 0);
        System.out.println(resolvableType0.resolve());
        System.out.println(resolvableType0.getType());
        // 获取方法的第二个参数对应的ResolvableType
        ResolvableType resolvableType1 = ResolvableType.forMethodParameter(test, 1);
        System.out.println(resolvableType1.resolve());
        System.out.println(resolvableType1.getType());


        /*ResolvableType resolvableType = ResolvableType.forClass(HashMap.class, C.class);
        ResolvableType[] generics = resolvableType.getGenerics();
        System.out.println("generics is " + Arrays.toString(generics));
        Type type = resolvableType.getType();
        System.out.println("type is :" + type);
        Class<?> rawClass = resolvableType.getRawClass();
        System.out.println("rawClass is " + rawClass);
        Object source = resolvableType.getSource();
        System.out.println("source is " + source);

        System.out.println( "==================================================================");

        ResolvableType resolvableType1 = ResolvableType.forClass(C.class);
        ResolvableType[] generics1 = resolvableType1.getGenerics();
        System.out.println("generics1 is " + Arrays.toString(generics1));
        Object source1 = resolvableType1.getSource();
        System.out.println("source1 is " + source1);
        Class<?> rawClass1 = resolvableType1.getRawClass();
        System.out.println("rawClass1 is " + rawClass1);
        Class<? extends ResolvableType> aClass = resolvableType1.getClass();
        System.out.println("class is " + aClass);*/
    }

}
