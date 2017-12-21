package com.server;

import com.server.util.ConnDB;

public class Test {
    public static void main(String[] args) {
        ConnDB connDB = new ConnDB();
        connDB.setConnection();
    }
}
