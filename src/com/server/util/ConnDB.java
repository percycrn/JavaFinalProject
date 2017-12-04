package com.server.util;

import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;

public class ConnDB {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ChatSystem;";
    private static final String USERNAME = "chenruonan";
    private static final String PASSWORD = "crn199613";
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // connect DB connection
    public void setConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // check whether the client exists
    public boolean clientAlreadyExists(String clientName) {
        try {
            resultSet = statement.executeQuery("SELECT CLIENTNAME FROM CLIENT WHERE CLIENTNAME = '" + clientName + "'");
            if (!resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // return password
    public String checkPassword(String clientName) throws SQLException {
        resultSet = statement.executeQuery("SELECT PASSWORD FROM CLIENT WHERE CLIENTNAME = '" + clientName + "'");
        resultSet.next();
        return resultSet.getString("PASSWORD").trim();
    }

    // store new client
    public void storeNewClient(String clientName, String password) throws SQLException {
        statement.executeUpdate("INSERT INTO CLIENT(clientName,password) VALUES ('" + clientName + "','" + password + "')");
    }

    // initialize the list of friends
    //
    @SuppressWarnings("unchecked")
    public ResultSet initFriendList(String clientName) throws SQLException {
        return statement.executeQuery("SELECT friendName FROM FRIEND WHERE CLIENTNAME ='" + clientName + "'");
    }

    // add friend to DB
    public void addFriendToDB(String clientName, String friendName) throws SQLException {
        statement.executeUpdate("INSERT INTO FRIEND VALUES ('" + clientName + "','" + friendName + "')");
        statement.executeUpdate("INSERT INTO FRIEND VALUES ('" + friendName + "','" + clientName + "')");
    }

    // close DB connection
    private void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
