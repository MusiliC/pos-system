package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.service.PaymentService;
import com.systechafrica.service.PosService;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PosServiceImplTest {

    private PosService posService;

    @BeforeEach
    void setup() {
        DatabaseHandler databaseHandler = new DatabaseHandlerMySqlImpl();
        Logger logger = Logger.getLogger(AuthenticationServiceImplTest.class.getName());
        AuthenticationService authenticationService = new AuthenticationServiceImpl(databaseHandler, logger);
        PaymentService paymentService = new PaymentServiceImpl(databaseHandler, logger);
        posService = new PosServiceImpl(authenticationService, paymentService, logger);
    }

    @Test
    @Disabled
    void startApplication() {
        posService.startApplication();
    }
}