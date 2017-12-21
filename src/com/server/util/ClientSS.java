package com.server.util;

import com.server.ManageServer;

import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClientSS extends ManageServer implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String clientName;
    private static ConnDB connDB;

    static {
        connDB = new ConnDB();
        connDB.setConnection();
    }

    ClientSS(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void storeClientAccount() {
        map.put(clientName, socket);
        messageBottom.add(df.format(new Date()) + " new connection, client name: " + clientName);
        messageBottom.add(df.format(new Date()) + " quantity of clients " + map.size());
    }

    @SuppressWarnings("unchecked")
    private void executeTransaction() throws IOException {
        try {
            String message = in.readUTF();
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
                    StringBuilder initFriendList = new StringBuilder();
                    while (resultSet.next()) {
                        initFriendList = initFriendList.append(resultSet.getString("friendName")).append(",");
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
                    String targetName = in.readUTF();
                    String dialog = in.readUTF();
                    messageBottom.add(df.format(new Date()) + " From [" + clientName + "] to [" + targetName + "] " + dialog);
                    out = new DataOutputStream(map.get(targetName).getOutputStream());
                    out.writeUTF(dialog);
                    out.flush();
                    break;
                case "@Logout@":
                    messageBottom.add(df.format(new Date()) + " client[" + clientName + "] logout");
                    map.remove(clientName);
                    messageBottom.add(df.format(new Date()) + " quantity of clients " + map.size());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        while (true) {
            try {
                executeTransaction();
            } catch (IOException e) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
        }
    }
}