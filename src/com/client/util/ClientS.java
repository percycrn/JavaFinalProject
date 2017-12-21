package com.client.util;

import com.client.ManageClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientS extends ManageClient {

    public ClientS(String path, int port, String myClientName) {
        ManageClient.clientName = myClientName;
        try {
            socket = new Socket(path, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //receiveMessage();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void receiveMessage() {
        new Thread(() -> {
            while (true) {
                try {
                    String message = in.readUTF();
                    if (!message.equals("")) {
                        ManageClient.leftMessage.add(message);
                        ManageClient.rightMessage.add(" ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF("@SendMessage@");
            out.flush();
            out.writeUTF(ManageClient.targetName);
            out.flush();
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        try {
            out.writeUTF("@Logout@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}