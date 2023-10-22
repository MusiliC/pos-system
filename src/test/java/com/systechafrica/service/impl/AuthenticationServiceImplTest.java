package com.systechafrica.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

class AuthenticationServiceImplTest {

    DatabaseHandler databaseHandler;
    private Logger logger;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        databaseHandler = new DatabaseHandlerMySqlImpl();
        logger = Logger.getLogger(AuthenticationServiceImpl.class.getName());
        authenticationService = new AuthenticationServiceImpl(databaseHandler, logger);
    }

    @Test
    @DisplayName("Authenticating user tests")
    void testAuthenticate() throws ClassNotFoundException, SQLException {
        //prepare test data
        // boolean isAuthentic = authenticationService.authenticate("Musili", "Musili123");
        //  boolean isAuthentic2 = authenticationService.authenticate("Musili2", "Musili123");
        Executable successExecutable = () -> assertTrue(authenticationService.authenticate("Musili", "Musili123"));
        Executable failureExecutable = () -> assertFalse(authenticationService.authenticate("unknown user", "unknown password"));
        assertAll(
                successExecutable,
                failureExecutable

        );
    }
}