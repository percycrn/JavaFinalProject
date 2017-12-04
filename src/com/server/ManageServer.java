package com.server;

import com.server.util.ConnDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;

public class ManageServer {
    protected static int numOfUsers = 0;
    protected static String PCPort;
    protected static String clientName;
    protected static SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    protected static ObservableList messageTop = FXCollections.observableArrayList();
    protected static ObservableList messageBottom = FXCollections.observableArrayList();
    protected static ConnDB connDB;

    static {
        connDB = new ConnDB();
        connDB.setConnection();
    }
}