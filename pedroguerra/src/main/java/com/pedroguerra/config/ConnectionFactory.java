package com.pedroguerra.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/pedroguerra";
    private static final String USER = "root";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");

        if (password == null) {
            throw new SQLException("Variável de ambiente DB_PASSWORD não foi definida.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado!", e);
        }
    }
}
