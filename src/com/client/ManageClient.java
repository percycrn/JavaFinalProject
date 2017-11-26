package com.client;

import com.client.util.ClientS;
import com.server.util.ConnDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;

public class ManageClient {
    protected static ClientS clientS;
    protected static String clientName;
    protected static String targetName;
    protected static ConnDB connDB;
    protected static ObservableList<String> leftMessage = FXCollections.observableArrayList();
    protected static ObservableList<String> rightMessage = FXCollections.observableArrayList();
    protected static ObservableList<String> friendList = FXCollections.observableArrayList();
    protected static boolean friendListInitFlag = false;
    protected SimpleDateFormat df = new SimpleDateFormat("YY-MM-DD HH-MM-SS");

    static {
        connDB = new ConnDB();
        connDB.setConnection();
    }
}
