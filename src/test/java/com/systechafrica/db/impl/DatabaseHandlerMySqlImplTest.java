package com.systechafrica.db.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("connect() - when correct credentials then a connection object should be returned")
    void connect_success () throws SQLException,ClassNotFoundException {
        // data
        // invoke
        Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD);
        // verify
        assertNotNull(connection);

    }

    @Test
    @DisplayName("connect() - when wrong password is supplied then an SQLException should be thrown")
    void connect_Sql_Exception() throws SQLException, ClassNotFoundException {
        // data

        // invoke
        Throwable exception = assertThrows(SQLException.class,
                () -> databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, "Wrong password"));

        // verify
        assertTrue(exception.getMessage().contains("Access denied for user"));

    }

    @Test
    @DisplayName("connect() - when no jdbc driver found then an ClassNotFoundException  should be thrown")
    @Disabled
    void connect_Class_not_Found_Exception() throws SQLException, ClassNotFoundException {
        Throwable exception = assertThrows(ClassNotFoundException.class,
                () -> databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD));

    }
}