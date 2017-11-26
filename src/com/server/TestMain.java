package com.server;

import com.server.util.ConnDB;

import java.sql.SQLException;

public class TestMain {
    public static void main(String[] args) throws SQLException {
        // new ServerS(10086);
        ConnDB connDB = new ConnDB();
        connDB.setConnection();
        System.out.println(connDB.checkPassword("admin"));
    }
}
