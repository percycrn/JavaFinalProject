package com.client.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientS {
    private String myClientName;
    private String targetName;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientS(String path, int port, String myClientName) {
        this.myClientName = myClientName;
        try {
            Socket socket = new Socket(path, port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendName();
    }

    private void sendName() {
        try {
            out.writeUTF(myClientName);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String targetName) {
        try {
            out.writeUTF(myClientName);
            out.flush();
            out.writeUTF(targetName);
            out.flush();
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();

            /*
            if (str.equalsIgnoreCase("l")) {
                try {
                    out.writeUTF(str);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("=========");
                MyServerReader reader = new MyServerReader(in);
                MyServerWriter writer = new MyServerWriter(out, str);
                reader.start();
                writer.start();
                try {
                    writer.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (str.equalsIgnoreCase("W")) {
                try {
                    out.writeUTF(str);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MyServerReader reader = new MyServerReader(in);
                MyServerWriter writer = new MyServerWriter(out, str);
                reader.start();
                writer.start();
                try {
                    writer.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (str.equalsIgnoreCase("t")) {
                try {
                    out.writeUTF(str);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("退出系统");
                System.exit(0);
            }
            */
        }
    }
}
