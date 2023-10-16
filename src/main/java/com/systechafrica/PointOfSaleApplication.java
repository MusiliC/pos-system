package com.systechafrica;

import com.systechafrica.service.PosService;
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
        PosService posService = new PosServiceImpl(LOGGER);
        posService.startApplication();


    }

}