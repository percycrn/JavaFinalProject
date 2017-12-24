package com.server.util;

import com.server.ManageServer;

import javax.swing.*;
import java.net.*;
import java.util.Date;

public class ServerS extends ManageServer {
    private static ServerSocket serverSocket;
    @SuppressWarnings("unchecked")
    public ServerS(int port) {
        try {
            serverSocket = new ServerSocket(port);
            messageBottom.add(df.format(new Date()) + " start server in PCPort " + PCPort);
            messageBottom.add(df.format(new Date()) + " current clients " + map.size());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "PCPort已被占用");
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void startListener() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new ClientSS(socket)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}