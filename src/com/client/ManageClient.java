package com.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ManageClient {
    protected static String host;
    protected static int port;
    protected static String clientName;
    protected static String targetName;
    protected ObservableList<String> leftMessage = FXCollections.observableArrayList();
    protected ObservableList<String> rightMessage = FXCollections.observableArrayList();
    protected static ObservableList<String> friendList = FXCollections.observableArrayList();
    protected static DataInputStream in;
    protected static DataOutputStream out;
    protected static Socket socket;
    protected static boolean whileList = false;

    protected void close() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
