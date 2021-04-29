package com.faithgreen.data;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class DataApplicationTests {

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        DruidDataSource source = (DruidDataSource) dataSource;
        System.out.println("initalSize: " + source.getInitialSize());
        System.out.println(this.dataSource);
        Connection connection = this.dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
