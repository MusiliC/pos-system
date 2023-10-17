package com.systechafrica.service.impl;

import com.systechafrica.model.Payment;
import com.systechafrica.service.PaymentService;
import java.util.logging.Logger;

public class PaymentServiceImpl implements PaymentService {
    private Logger logger;

    PaymentServiceImpl(Logger logger){
        this.logger=logger;
    }
    @Override
    public void savePayment(Payment payment) {
        logger.info("Saving payment to the db...");
    }
}
