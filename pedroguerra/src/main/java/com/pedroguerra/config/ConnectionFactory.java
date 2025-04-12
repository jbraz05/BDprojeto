package com.pedroguerra.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionFactory {

    private static final String CONFIG_FILE = "/config.properties";

    public static Connection getConnection() throws SQLException {
        try (InputStream input = ConnectionFactory.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new SQLException("Arquivo config.properties não encontrado.");
            }

            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado!", e);
        } catch (Exception e) {
            throw new SQLException("Erro ao carregar config.properties.", e);
        }
    }
}
