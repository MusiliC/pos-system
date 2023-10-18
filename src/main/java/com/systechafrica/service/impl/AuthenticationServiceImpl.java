package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthenticationServiceImpl implements AuthenticationService {

    private DatabaseHandler databaseHandler;
    private Logger logger;

    public AuthenticationServiceImpl(DatabaseHandler databaseHandler, Logger logger) {
        this.databaseHandler = databaseHandler;
        this.logger = logger;
    }

    @Override
    public boolean authenticate(String username, String password) throws SQLException, ClassNotFoundException {
        logger.info("Initializing authentication for " + username);
        Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD);
        boolean isAuthentic;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("select * from USERS where username=? and user_password=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            isAuthentic = false;
            while (resultSet.next()) {
                isAuthentic = true;
            }
            resultSet.close();
        }
        connection.close();
        return isAuthentic;
    }
}
