package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.db.impl.DatabaseHandlerMySqlImpl;
import com.systechafrica.service.AuthenticationService;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService {

    DatabaseHandler databaseHandler;
    public AuthenticationServiceImpl(){
        databaseHandler = new DatabaseHandlerMySqlImpl();
    }
    @Override
    public boolean authenticate(String username, String password) throws SQLException, ClassNotFoundException {
        Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD);
        boolean isAuthentic;
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from USERS where username=? and user_password=?")) {
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
