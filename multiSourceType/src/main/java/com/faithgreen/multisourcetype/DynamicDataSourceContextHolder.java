package com.faithgreen.multisourcetype;

/**
 * 创建一个数据源切换处理类，有对数据源变量的获取、设置和情况的方法，其中 threadlocal 用于保存某个线程共享变量。
 */
public class DynamicDataSourceContextHolder {

    /**
     * 使用 ThreadLocal 维护变量，为每个使用该变量的线程提供独立的变量副本
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源变量
     *
     * @param dataSourceType 数据源变量的 key
     */
    public static void setDataSourceType(String dataSourceType) {
        System.out.printf("切换到 {%s} 数据源", dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源变量
     *
     * @return 数据源变量的 key
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
