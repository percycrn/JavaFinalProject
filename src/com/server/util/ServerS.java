package com.server.util;

import com.server.ManageServer;

import javax.swing.*;
import java.net.*;
import java.util.Date;

public class ServerS extends ManageServer {
    private static ServerSocket serverSocket;

    @SuppressWarnings("all")
    public ServerS(int port) {
        try {
            serverSocket = new ServerSocket(port);
            getMessageTop().add("当前用户 " + numOfUsers + " 人");
            getMessageBottom().add(df.format(new Date()) + " success to start server in PC port " + PCPort);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "PCPort已被占用");
            return;
        }
        while (true) {
            listener();
        }
    }

    private void listener() {
        try {
            Socket socket = serverSocket.accept();
            numOfUsers++;
            new Thread(new ClientSS(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}