package com.revature.pn.common.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Implements the Factory and Singleton design patterns
    public class ConnectionFactory {

        private static Logger logger = LogManager.getLogger(ConnectionFactory.class);

        private static ConnectionFactory connFactory;
        private Properties dbProps = new Properties();

        private ConnectionFactory() {
            // include logic here to set up the connection factory's DB details (read from the properties file)

            try {
                Class.forName("org.postgresql.Driver");
                dbProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            } catch (IOException e) {

                throw new RuntimeException("could not read from properties file.", e);
            } catch (ClassNotFoundException e) {

                throw new RuntimeException("Failed to load PostgreSQL JDBC driver.", e);
            }

        }

    public static ConnectionFactory getInstance() {
        if (connFactory == null) {
            connFactory = new ConnectionFactory();
        }
        return connFactory;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbProps.getProperty("db-url"), dbProps.getProperty("db-username"), dbProps.getProperty("db-password"));
    }

}