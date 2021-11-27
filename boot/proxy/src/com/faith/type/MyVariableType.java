package com.faith.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MyVariableType<T, K extends @TypeAnnotation Integer & Type> {

    public <U extends Long, V> void testTypeVariable(Map<U, V> map) {

    }

    public static void main(String[] args) {
        Class<MyVariableType> myVariableTypeClass = MyVariableType.class;
        TypeVariable<Class<MyVariableType>>[] typeParameters = myVariableTypeClass.getTypeParameters();
        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<Class<MyVariableType>> typeParameter = typeParameters[i];
            Type[] bounds = typeParameter.getBounds();
            String name = typeParameter.getName();
            AnnotatedType[] annotatedBounds = typeParameter.getAnnotatedBounds();
            Class<MyVariableType> genericDeclaration = typeParameter.getGenericDeclaration();
            System.out.println("第" + (i + 1) + "个类型变量名称为：" + name);
            System.out.println("通过getBounds方法获取到，第" + (i + 1) + "个类型变量边界为：" + Arrays.toString(bounds));
            System.out.println("第" + (i + 1) + "个类型变量的声明的位置为：" + genericDeclaration);
            System.out.println("通过getAnnotationBounds方法获取到，第" + (i + 1) + "个类型的边界为：" + Arrays.stream(annotatedBounds).map(AnnotatedType::getType).collect(Collectors.toList()));

            for (AnnotatedType annotatedBound : annotatedBounds) {
                Annotation[] annotations = annotatedBound.getAnnotations();
                if (annotations.length > 0) {
                    System.out.println("第" + (i + 1) + "个类型变量的上界上添加了注解，注解为：" + annotations[i]);
                }
            }
        }

        System.out.println("========================基于方法获取变量类型");

        Method[] declaredMethods = myVariableTypeClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            TypeVariable<Method>[] typeParameters1 = declaredMethod.getTypeParameters();
            int i = 1;
            for (TypeVariable<Method> methodTypeVariable : typeParameters1) {
                System.out.println("方法：" + name + "的第2" + (i++) + "个类型变量为：" + methodTypeVariable.getName());
            }
        }
    }
}
