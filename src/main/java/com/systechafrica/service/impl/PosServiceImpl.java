package com.systechafrica.service.impl;

import com.systechafrica.service.AuthenticationService;
import com.systechafrica.service.PosService;
import com.systechafrica.util.Config;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class PosServiceImpl implements PosService {

    private Logger logger;

    private AuthenticationService authenticationService;

    Scanner scanner;

    public PosServiceImpl(Logger logger) {
        scanner = new Scanner(System.in);
        this.logger = logger;
        authenticationService = new AuthenticationServiceImpl();
    }

    @Override
    public void startApplication() {
        logger.info("In the PosServiceImpl ");

        try {
            boolean isLoggedIn = authenticateUser();
            logger.info("User authenticated: "+isLoggedIn);

        } catch (SQLException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }


    }

    private boolean authenticateUser() throws SQLException, ClassNotFoundException {
        logger.info("Authenticating the user");

        int loginEntries=1;
        while (loginEntries <= Config.LOGIN_RETRIES_LIMIT) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            if (authenticationService.authenticate(username,password)) {
                return true;
            }
            loginEntries++;
            logger.info("user enetered wrong credentials!!!");
            System.out.println();
        }
        return false;


    }
}
