package com.server.util;

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
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
        statement.executeUpdate("INSERT INTO CLIENT VALUES ('" + clientName + "','" + password + "',0)");
    }

    // initialize the list of friends
    @SuppressWarnings("unchecked")
    public ResultSet initFriendList(String clientName) throws SQLException {
        return statement.executeQuery("SELECT FRIENDNAME FROM FRIEND WHERE CLIENTNAME ='" + clientName + "'");
    }

    // add friend to DB
    public void addFriendToDB(String clientName, String friendName) throws SQLException {
        statement.executeUpdate("INSERT INTO FRIEND VALUES ('" + clientName + "','" + friendName + "')");
        statement.executeUpdate("INSERT INTO FRIEND VALUES ('" + friendName + "','" + clientName + "')");
    }

    // delete friend
    public boolean deleteFriend(String clientName, String friendName) {
        try {
            statement.executeUpdate("DELETE FROM FRIEND WHERE clientName = '" + clientName + "' AND friendName = '" + friendName + "'");
            statement.executeUpdate("DELETE FROM FRIEND WHERE clientName = '" + friendName + "' AND friendName = '" + clientName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // get login status
    public int getLoginStatus(String clientName) throws SQLException {
        resultSet = statement.executeQuery("SELECT STATUS FROM CLIENT WHERE CLIENTNAME = '" + clientName + "'");
        resultSet.next();
        return Integer.valueOf(resultSet.getString("STATUS"));
    }

    // set login status
    public void setLoginStatus(String clientName, int i) throws SQLException {
        if (i == 0) {
            statement.executeUpdate("UPDATE CLIENT SET STATUS = 0 WHERE CLIENTNAME = '" + clientName + "'");
        } else if (i == 1) {
            statement.executeUpdate("UPDATE CLIENT SET STATUS = 1 WHERE CLIENTNAME = '" + clientName + "'");
        }
    }

    // init login status
    public void setAllLogout() throws SQLException {
        statement.executeUpdate("UPDATE CLIENT SET STATUS = 0");
    }

    // add History
    public void addHistory(String clientName, String targetName, String message, String time) throws SQLException {
        statement.executeUpdate("INSERT INTO HISTORY " +
                "VALUES('" + clientName + "','" + targetName + "','" + message + "','" + time + "')");
    }

    // get History
    public String getHistory(String clientName, String targetName) throws SQLException {
        StringBuilder message = new StringBuilder();
        resultSet = statement.executeQuery("SELECT * FROM HISTORY " +
                "WHERE (CLIENTNAME = '" + clientName + "' and TARGETNAME = '" + targetName + "') " +
                "OR (CLIENTNAME = '" + targetName + "' and TARGETNAME = '" + clientName + "') ");
        while (resultSet.next()) {
            message = message.append(resultSet.getString("TIME").trim())
                    .append(" From [").append(resultSet.getString("CLIENTNAME").trim())
                    .append("] to [").append(resultSet.getString("TARGETNAME").trim())
                    .append("] ").append(resultSet.getString("MESSAGE").trim()).append("@!@");
        }
        return String.valueOf(message);
    }
}
