package com.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;

public class ManageServer{
    protected static int numOfUsers = 0;
    protected static String PCPort;
    protected SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private static ObservableList messageTop = FXCollections.observableArrayList();
    private static ObservableList messageBottom = FXCollections.observableArrayList();

    protected static ObservableList getMessageTop() {
        return messageTop;
    }

    protected static ObservableList getMessageBottom() {
        return messageBottom;
    }

}