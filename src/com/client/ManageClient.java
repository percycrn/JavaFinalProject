package com.client;

import com.client.util.ClientS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class ManageClient {
    protected static String host;
    protected static int port;
    protected static ClientS clientS;
    protected static String clientName;
    protected static String targetName;
    protected static ObservableList<String> leftMessage = FXCollections.observableArrayList();
    protected static ObservableList<String> rightMessage = FXCollections.observableArrayList();
    protected static ObservableList<String> friendList = FXCollections.observableArrayList();
    protected static boolean friendListInitFlag = false;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Socket socket;
    protected SimpleDateFormat df = new SimpleDateFormat("YY-MM-DD HH-MM-SS");
}
