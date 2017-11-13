package com.server.util;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class ClientSS implements Runnable {
    private static HashMap<String, Socket> map = new HashMap<>();
    private String clientName = null;
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

    private void storeClientAccount() {
        try {
            clientName = in.readUTF();
            map.put(clientName, socket);
            System.out.println("new connection\nclient name " + clientName);
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
                System.out.println("From [" + clientName + "] to [" + targetName + "] " + message);
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