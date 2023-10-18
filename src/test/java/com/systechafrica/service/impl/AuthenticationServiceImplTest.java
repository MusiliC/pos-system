package com.systechafrica.service.impl;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {

    DatabaseHandler databaseHandler;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        databaseHandler = new DatabaseHandlerMySqlImpl();
        Logger logger = Logger.getLogger(AuthenticationServiceImplTest.class.getName());
        authenticationService = new AuthenticationServiceImpl(databaseHandler, logger);
    }

    @Test
    @DisplayName("AuthenticationService - authenticate")
    void testAuthenticate() throws ClassNotFoundException, SQLException {
        //prepare test data

       /*  boolean isAuthentic =  authenticationService.authenticate("mhusika", "Admin123");
           boolean isAuthentic2 =  authenticationService.authenticate("mhusika2", "Admin123");
       */
        Executable successExecutable = () -> assertTrue(authenticationService.authenticate("mhusika", "Admin123"));
        Executable failureExecutable = () -> assertFalse(authenticationService.authenticate("unknown user", "unknown password"));

        assertAll(
                successExecutable,
                failureExecutable
        );

    }
}
