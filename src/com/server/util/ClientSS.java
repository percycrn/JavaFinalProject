package com.server.util;

import com.server.ManageServer;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientSS extends ManageServer implements Runnable {
    private static HashMap<String, Socket> map = new HashMap<>();
    private String clientName = null;
    private String targetName;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    ClientSS(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storeClientAccount();
    }

    @SuppressWarnings("unchecked")
    private void storeClientAccount() {
        try {
            clientName = in.readUTF();
            map.put(clientName, socket);
            getMessageBottom().add(df.format(new Date())+" new connection, client name: " + clientName);
            getMessageTop().add(clientName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            forwardMessage();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                in.close();
                out.close();
                map.remove(clientName);
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void forwardMessage() throws IOException {
        // System.out.println("start chatting");
        // out = new DataOutputStream(map.get(clientName).getOutputStream());
        String clientName;
        String targetName;
        String message;
        while (true) {
            try {
                clientName = in.readUTF();
                targetName = in.readUTF();
                message = in.readUTF();
                getMessageBottom().add(df.format(new Date())+" From [" + clientName + "] to [" + targetName + "] " + message);
                out = new DataOutputStream(map.get(targetName).getOutputStream());
                out.writeUTF(clientName);
                out.flush();
                out.writeUTF(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("聊天传输出错");
                break;
            }
        }
    }

}