package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.model.Payment;
import com.systechafrica.service.PaymentService;
import java.util.logging.Logger;

public class PaymentServiceImpl implements PaymentService {
    private Logger logger;

    private DatabaseHandler databaseHandler;

    public PaymentServiceImpl(DatabaseHandler databaseHandler,Logger logger) {
        this.logger = logger;
        this.databaseHandler=databaseHandler;
    }

    @Override
    public void savePayment(Payment payment) {
        logger.info("Saving payment to the db...");
    }
}
