package com.faith.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MySpecialType {

    SpecialType<ParameterizedType> specialType;

    public static void main(String[] args) {

        Class<MySpecialType> mySpecialTypeClass = MySpecialType.class;
        Field[] declaredFields = mySpecialTypeClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Type genericType = declaredField.getGenericType();
            if (genericType instanceof ParameterizedType) {
                System.out.println("specialType是一个参数化类型，类型是：" + genericType);
            } else {
                System.out.println("specialType不是一个参数化类型，类型是：" + genericType);
            }
        }
    }
}
