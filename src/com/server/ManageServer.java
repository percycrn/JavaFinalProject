package com.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ManageServer {
    protected static String PCPort;
    protected static HashMap<String, Socket> map = new HashMap<>();
    protected static ObservableList messageTop = FXCollections.observableArrayList();
    protected static ObservableList messageBottom = FXCollections.observableArrayList();
    protected static SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
}