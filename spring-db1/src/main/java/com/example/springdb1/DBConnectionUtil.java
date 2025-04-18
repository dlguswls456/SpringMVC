package com.example.springdb1;

import static com.example.springdb1.ConnectionConst.PASSWORD;
import static com.example.springdb1.ConnectionConst.URL;
import static com.example.springdb1.ConnectionConst.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DBConnectionUtil {

    // JDBC 표준 인터페이스가 제공하는 Connection
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());

            return connection;
        } catch (SQLException e) {
            log.info("error={}", e);
            throw new IllegalStateException();
        }
    }

}
