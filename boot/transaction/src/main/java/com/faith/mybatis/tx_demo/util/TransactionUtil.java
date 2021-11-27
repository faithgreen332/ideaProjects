package com.faith.mybatis.tx_demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionUtil {

    public static final ThreadLocal<Connection> synchronousConnection = new ThreadLocal<>();

    private TransactionUtil() {
    }

    public static Connection startTransaction() {
        Connection connection = synchronousConnection.get();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://192.168.228.132:3306/mysql?useSSL=false&characterEncoding=utf-8", "root", "Faithgreen");
                synchronousConnection.set(connection);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static int execute(String sql, Object... args) {
        Connection connection = synchronousConnection.get();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (args != null) {
                for (int i = 1; i < args.length + 1; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void commit() {
        try (Connection connection = synchronousConnection.get()) {
            connection.commit();
            synchronousConnection.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        try (Connection connection = synchronousConnection.get()) {
            connection.rollback();
            synchronousConnection.remove();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
