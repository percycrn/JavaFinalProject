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
            messageTop.add("当前用户 " + map.size() + " 人");
            messageBottom.add(df.format(new Date()) + " success to start server in PC port " + PCPort);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "PCPort已被占用");
        }
    }

    @SuppressWarnings("unchecked")
    private void listener() {
        try {
            Socket socket = serverSocket.accept();
            new Thread(new ClientSS(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void startListener() {
        while (true) {
            listener();
        }
    }
}