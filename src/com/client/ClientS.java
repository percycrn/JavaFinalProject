package com.client;

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
        send();
        laugh();
    }

    private void send() {
        String s = null;
        try {
            out.writeUTF(name);
            out.flush();
            s = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("在线列表" + s);
    }

    private void laugh() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("聊天请输入:l 文件传输请输入:w 退出请输入:t");
            String str = scanner.nextLine();
            scanner.close();
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
        }
    }
}
