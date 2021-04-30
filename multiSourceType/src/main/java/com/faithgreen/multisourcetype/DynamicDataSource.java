package com.faithgreen.multisourcetype;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSource);
        // 方法调用时用来将 targetDataSource 的属性写入 resolvedDataSource中
        super.afterPropertiesSet();
    }

    /**
     * 根据 key 获取数据源的信息
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
