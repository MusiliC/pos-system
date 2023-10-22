package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.service.PaymentService;
import com.systechafrica.service.PosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PosServiceImplTest {


    private Logger logger;
    DatabaseHandler databaseHandler;
    private AuthenticationService authenticationService;
    private PaymentService paymentService;

    private PosService posService;

    @BeforeEach
    void setup(){
        databaseHandler = new DatabaseHandlerMySqlImpl();
        logger = Logger.getLogger(AuthenticationServiceImpl.class.getName());
        authenticationService = new AuthenticationServiceImpl(databaseHandler, logger);
        paymentService = new PaymentServiceImpl(databaseHandler, logger);
        posService = new PosServiceImpl(authenticationService,paymentService, logger);
    }

    @Test
    @Disabled
    void startApplication() {
        posService.startApplication();
    }
}