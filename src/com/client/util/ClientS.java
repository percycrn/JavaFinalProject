package com.client.util;

import com.client.ManageClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientS extends ManageClient {
    private DataInputStream in;
    private DataOutputStream out;

    public ClientS(String path, int port, String myClientName) {
        setClientName(myClientName);
        try {
            Socket socket = new Socket(path, port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendName();
        receiveMessage();
    }

    private void sendName() {
        try {
            out.writeUTF(getClientName());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void receiveMessage() {
        new Thread(() -> {
            while (true) {
                try {
                    if (!in.readUTF().equals("")) {
                        getLeftMessage().add(in.readUTF());
                        getRightMessage().add(" ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String message, String targetName) {
        try {
            out.writeUTF(getClientName());
            out.flush();
            out.writeUTF(targetName);
            out.flush();
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}