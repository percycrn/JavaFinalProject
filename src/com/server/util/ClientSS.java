package com.server.util;

import com.server.ManageServer;

import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClientSS extends ManageServer {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private DataOutputStream transfer;
    private String clientName;
    private boolean whileFlag = true;
    private static ConnDB connDB;

    static {
        connDB = new ConnDB();
        connDB.setConnection();
        try {
            connDB.setAllLogout();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        messageBottom.add(df.format(new Date()) + " client [ " + clientName + "] login");
        messageBottom.add(df.format(new Date()) + " current clients " + map.size());
    }

    @SuppressWarnings("unchecked")
    private void executeTransaction() throws IOException, SQLException {
        String message = in.readUTF();
        System.out.println(message);
        switch (message) {
            case "@Set@":
                out.writeUTF("@AbleToConnect@");
                out.flush();
                whileFlag = false;
                break;
            case "@Login@":
                String clientNameLogin = in.readUTF();
                String passwordLogin = in.readUTF();
                if (!connDB.clientAlreadyExists(clientNameLogin)) {
                    out.writeUTF("@ClientNotExist@");
                    out.flush();
                    whileFlag = false;
                } else if (!connDB.checkPassword(clientNameLogin).equals(passwordLogin)) {
                    out.writeUTF("@IncorrectPassword@");
                    out.flush();
                    whileFlag = false;
                } else if (connDB.getLoginStatus(clientNameLogin) == 1) {
                    out.writeUTF("@AlreadyLogin@");
                    out.flush();
                    whileFlag = false;
                } else {
                    clientName = clientNameLogin;
                    storeClientAccount();
                    connDB.setLoginStatus(clientName, 1);
                    out.writeUTF("@SuccessLogin@");
                    out.flush();
                    out.writeUTF(clientName);
                    out.flush();
                }
                break;
            case "@AddFriend@":
                String friendName = in.readUTF();
                if (connDB.clientAlreadyExists(friendName)) {
                    connDB.addFriendToDB(clientName, friendName);
                    out.writeUTF("@SuccessToAddFriend@");
                    out.flush();
                    out.writeUTF(friendName);
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
                    initFriendList = initFriendList.append(resultSet.getString("friendName").trim()).append(",");
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
                connDB.addHistory(clientName, targetName, dialog, df.format(new Date()));
                transfer = new DataOutputStream(map.get(targetName).getOutputStream());
                transfer.writeUTF("@NewMessage@");
                transfer.flush();
                transfer.writeUTF(dialog);
                transfer.flush();
                break;
            case "@Logout@":
                messageBottom.add(df.format(new Date()) + " client[" + clientName + "] logout");
                connDB.setLoginStatus(clientName, 0);
                map.remove(clientName);
                messageBottom.add(df.format(new Date()) + " quantity of clients " + map.size());
                break;
            case "@DeleteFriend@":
                String deleteFriendName = in.readUTF();
                if (connDB.deleteFriend(clientName, deleteFriendName)) {
                    out.writeUTF("@SuccessToDeleteFriend@");
                    out.flush();
                } else {
                    out.writeUTF("@FailToDeleteFriend@");
                    out.flush();
                }
                break;
            case "@StopWhile@":
                out.writeUTF("@StopWhile@");
                out.flush();
                break;
            case "@InitHistory@":
                String targetNameHistory = in.readUTF();
                out.writeUTF(connDB.getHistory(clientName,targetNameHistory));
                out.flush();
                break;
        }
    }

    @Override
    public void run() {
        while (whileFlag) {
            try {
                executeTransaction();
            } catch (IOException | SQLException e) {
                whileFlag = false;
                e.printStackTrace();
            }
        }
    }
}