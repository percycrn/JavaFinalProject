package com.client.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientS {
    private String path;
    private int port;
    private String name;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner = new Scanner(System.in);

    public ClientS(String path, int port, String name) {
        this.path = path;
        this.port = port;
        this.name = name;
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
        //String s = null;
        try {
            out.writeUTF(name);
            out.flush();
            //s = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("在线列表" + s);
    }

    public void sendMessage(String message) {
        try {
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
