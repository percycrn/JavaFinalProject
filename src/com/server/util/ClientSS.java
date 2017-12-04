package com.server.util;

import com.server.ManageServer;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class ClientSS extends ManageServer implements Runnable {
    private static HashMap<String, Socket> map = new HashMap<>();
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
        System.out.println(6);
        try {
            executeTransaction();
            System.out.println(7);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void storeClientAccount(String clientName) {
        map.put(clientName, socket);
        messageBottom.add(df.format(new Date()) + " new connection, client name: " + clientName);
        messageTop.add(clientName);
    }

    private void executeTransaction() throws SQLException {
        try {
            String message = in.readUTF();
            System.out.println(message);
            switch (message) {
                case "@Login@":
                    String clientName = in.readUTF();
                    String password = in.readUTF();
                    if (!connDB.checkClientExist(clientName)) {
                        out.writeUTF("@ClientNotExist@");
                        out.flush();
                    } else if (!connDB.checkPassword(clientName).equals(password)) {
                        out.writeUTF("@IncorrectPassword@");
                    } else {
                        System.out.println(7);
                        storeClientAccount(clientName);
                        System.out.println(8);
                        out.writeUTF("@SuccessLogin@");
                        out.flush();
                        System.out.println(9);
                    }
                    break;
                case "@AddFriend@":
                    break;
                case "@Register@":
                    break;
                case "@SendMessage@":
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        forwardMessage();
    }

    @SuppressWarnings("unchecked")
    private void forwardMessage() {
        // System.out.println("start chatting");
        // out = new DataOutputStream(map.get(clientName).getOutputStream());
        String targetName;
        String message;
        while (true) {
            try {
                targetName = in.readUTF();
                System.out.println(targetName);
                message = in.readUTF();
                System.out.println(targetName);
                System.out.println(message);
                messageBottom.add(df.format(new Date()) + " From [" + clientName + "] to [" + targetName + "] " + message);
                out = new DataOutputStream(map.get(targetName).getOutputStream());
                System.out.println("-----------------");
                System.out.println(targetName);
                System.out.println(message);
                out.writeUTF(targetName);
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