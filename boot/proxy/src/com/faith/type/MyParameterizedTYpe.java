package com.faith.type;

import java.util.List;
import java.util.Map;

public class MyParameterizedTYpe extends OwnerTypeDemo<String> {
    private List<String> stringList;

    private Map<String, String> stringStringMap;

    private Map.Entry<String, ?> entry;

    private OwnerTypeDemo<String>.Test<String> testOwnerType;

    private List list;

    private Map map;

    public void test(List<String> stringList, List list) {
    }

    /*public static void main(String[] args) {
        Class<MyParameterizedTYpe> myParameterizedTYpeClass = MyParameterizedTYpe.class;
        Field[] declaredFields = myParameterizedTYpeClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            Type genericType = declaredField.getGenericType();
            String typeName = genericType.getTypeName();
            String name = declaredField.getName();
            if (genericType instanceof ParameterizedType) {
                System.out.println(name + "是一个参数化类型，类型名称是：" + typeName);
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                System.out.println(name + "的 actualTypeAuguments:" + Arrays.toString(actualTypeArguments));
                Type ownerType = parameterizedType.getOwnerType();
                System.out.println(name + "的ownerType：" + ownerType);
                Type rawType = parameterizedType.getRawType();
                System.out.println(name + "的rawType：" + rawType);
            } else {
                System.out.println(name + "不是一个参数化类型，类型名称是：" + typeName);
            }
        }

        System.out.println("=======================================开始测试方法中的参数=======================================");

        Method[] declaredMethods = myParameterizedTYpeClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
            for (int i = 0; i < genericParameterTypes.length; i++) {

                Type genericParameterType = genericParameterTypes[i];
                String typeName = genericParameterType.getTypeName();
                System.out.println("打印" + name + "方法的参数，第" + (i + 1) + "个参数为：" + genericParameterType);

                if (genericParameterType instanceof ParameterizedType) {
                    System.out.println("第" + (i + 1) + "个参数是参数化类型，类型名称为：" + typeName);
                } else {
                    System.out.println("第" + (i + 1) + "个参数不是参数化类型，类型名称为：" + typeName);
                }

            }
        }

        System.out.println("开始测试父类中的泛型==================================");

        Type genericSuperclass = myParameterizedTYpeClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            System.out.println("父类是一个参数化类型，类型名称是：" + genericSuperclass.getTypeName());
        }
    }*/

    public static void main(String[] args) {
        int j = 0;
        j = j++;
        System.out.println(j);
    }


}
