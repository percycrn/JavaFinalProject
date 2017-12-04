package com.server.util;

import com.server.ManageServer;

import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
        try {
            executeTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void storeClientAccount() {
        map.put(clientName, socket);
        messageBottom.add(df.format(new Date()) + " new connection, client name: " + clientName);
        messageTop.add(clientName);
    }

    private void executeTransaction() throws SQLException {
        try {
            String message = in.readUTF();
            System.out.println(message);
            switch (message) {
                case "@Set@":
                    out.writeUTF("@AbleToConnect@");
                    out.flush();
                    break;
                case "@Login@":
                    String clientNameLogin = in.readUTF();
                    String passwordLogin = in.readUTF();
                    if (!connDB.clientAlreadyExists(clientNameLogin)) {
                        out.writeUTF("@ClientNotExist@");
                        out.flush();
                    } else if (!connDB.checkPassword(clientNameLogin).equals(passwordLogin)) {
                        out.writeUTF("@IncorrectPassword@");
                        out.flush();
                    } else {
                        clientName = clientNameLogin;
                        storeClientAccount();
                        out.writeUTF("@SuccessLogin@");
                        out.flush();
                    }
                    break;
                case "@AddFriend@":
                    String friendName = in.readUTF();
                    if (connDB.clientAlreadyExists(friendName)) {
                        connDB.addFriendToDB(clientName, friendName);
                        out.writeUTF("@SuccessToAddFriend@");
                        out.flush();

                    } else {
                        out.writeUTF("@FriendNotExist@");
                        out.flush();
                    }
                    break;
                case "@InitFriendList@":
                    ResultSet resultSet = connDB.initFriendList(clientName);
                    resultSet.beforeFirst();
                    StringBuilder initFriendList = new StringBuilder();
                    while (resultSet.next()) {
                        initFriendList.append(resultSet.getString("friendName")).append(",").trimToSize();
                    }
                    out.writeUTF(String.valueOf(initFriendList));
                    out.flush();
                    break;
                case "@Register@":
                    String clientNameRegister = in.readUTF();
                    String passwordRegister = in.readUTF();
                    if (connDB.clientAlreadyExists(clientNameRegister)) {
                        out.writeUTF("@ClientAlreadyExist@");
                        out.flush();
                    } else {
                        connDB.storeNewClient(clientNameRegister, passwordRegister);
                        out.writeUTF("@SuccessToRegister@");
                        out.flush();
                    }
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