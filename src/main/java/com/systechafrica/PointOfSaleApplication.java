package com.systechafrica;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.service.PaymentService;
import com.systechafrica.service.PosService;
import com.systechafrica.service.impl.AuthenticationServiceImpl;
import com.systechafrica.service.impl.PaymentServiceImpl;
import com.systechafrica.service.impl.PosServiceImpl;
import com.systechafrica.util.CustomFormatter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class PointOfSaleApplication {
    //Logger
    private static final Logger LOGGER = Logger.getLogger(PointOfSaleApplication.class.getName());

    public static void initializeLogger(){
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("log.txt");
            CustomFormatter formatter = new CustomFormatter();
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(formatter);
            LOGGER.info("Initialized application");
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        initializeLogger();
        DatabaseHandler databaseHandler = new DatabaseHandlerMySqlImpl();
        PaymentService paymentService = new PaymentServiceImpl(databaseHandler,LOGGER);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(databaseHandler,LOGGER);
        PosService posService = new PosServiceImpl(authenticationService,paymentService,LOGGER);
        posService.startApplication();


    }

}