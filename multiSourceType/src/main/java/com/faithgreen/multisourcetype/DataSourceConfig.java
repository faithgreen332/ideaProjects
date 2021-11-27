package com.faithgreen.multisourcetype;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "remoteDataSource")
    @ConfigurationProperties("spring.datasource.remote")
    public DataSource remoteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "localDataSource")
    @ConfigurationProperties("spring.datasource.local")
    @ConditionalOnProperty(prefix = "spring.datasource.local", name = "enabled", havingValue = "true")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource remote, DataSource local) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(MultiDataSourceTypeEnum.REMOTE.name(), remote);
        targetDataSource.put(MultiDataSourceTypeEnum.LOCAL.name(), local);
        return new DynamicDataSource(local, targetDataSource);
    }
}
