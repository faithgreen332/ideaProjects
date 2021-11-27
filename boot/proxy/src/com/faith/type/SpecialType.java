package com.faith.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SpecialType<T extends Type> {
    T t;

    public static void main(String[] args) {
        Class<SpecialType> specialTypeClass = SpecialType.class;
        Field[] declaredFields = specialTypeClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            Type genericType = declaredField.getGenericType();
            if (genericType instanceof ParameterizedType) {
                System.out.println("t 是一个参数化类型");
            } else {
                System.out.println("t 不是一个参数话类型");
            }
        }
    }
}
