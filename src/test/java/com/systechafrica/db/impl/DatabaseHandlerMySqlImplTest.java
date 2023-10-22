package com.systechafrica.db.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.util.Config;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerMySqlImplTest {

    DatabaseHandler databaseHandler;

    @BeforeEach
    void setup() {
        databaseHandler = new DatabaseHandlerMySqlImpl();

    }

    @AfterEach
    void releaseResources() {
        databaseHandler = null;
    }

    @Test
    @DisplayName("Connect() - when correct password returns connection object")
    void connect_success() throws SQLException, ClassNotFoundException {
        // data

        // invoke
        Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD);

        // verify
        assertNotNull(connection);
    }

    @Test
    @DisplayName("Connect() - when wrong password is applied - sqlException")
    void connect_sql_exception() throws SQLException, ClassNotFoundException {
        // data

        // invoke
        Throwable exception = assertThrows(SQLException.class,
                () -> databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, "123456"));

        // verify

        assertTrue(exception.getMessage().contains("Access denied for user"));
    }

    @Test
    @DisplayName("Connect() - when wrong password is applied - classNotException")
    @Disabled
    void connect_class_notFound() throws SQLException, ClassNotFoundException {
        // data

        // invoke
        Throwable exception = assertThrows(ClassNotFoundException.class,
                () -> databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD));

        // verify
    }
}