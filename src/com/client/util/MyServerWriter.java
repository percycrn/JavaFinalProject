package com.client.util;

import java.io.*;
import java.util.Scanner;

public class MyServerWriter extends Thread {
    private DataOutputStream dos;
    private String str;
    private Scanner scanner;

    public MyServerWriter(DataOutputStream dos, String str) {
        this.dos = dos;
        this.str = str;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        if (str.equalsIgnoreCase("l")) {
            chat();
        }
        if (str.equalsIgnoreCase("w")) {
            file();
        }
    }

    private void chat() {
        System.out.println("====进入聊天====");
        String info;
        try {
            while (true) {
                info = scanner.next();
                if (info.equals("bye")) {
                    System.out.println("结束与对方聊天");
                    break;
                }
                dos.writeUTF(info);
                dos.flush();
                info = scanner.next();
                dos.writeUTF(info);
                dos.flush();
                if (info.equals("bye")) {
                    System.out.println("结束与对方聊天");
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    private void file() {
        System.out.println("====进入文件传输====");
        String s = scanner.next();
        try {
            dos.writeUTF(s);
            dos.flush();
            System.out.println("输入本地文件路径：");
            String filePath = scanner.next();
            String fileName = filePath.substring(filePath.lastIndexOf("\\" + 1));
            dos.writeUTF(fileName);
            dos.flush();
            File file = new File(filePath);
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = in.read(bytes)) != -1) {
                dos.write(bytes, 0, i);
            }
            dos.flush();
            in.close();
            System.out.println("对" + s + "文件传输完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
