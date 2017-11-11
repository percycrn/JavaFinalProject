package com.client.util;

import java.io.*;

public class MyServerReader extends Thread {
    private DataInputStream dis;
    private String str;

    public MyServerReader(DataInputStream dis) {
        this.dis = dis;
    }

    @Override
    public void run() {
        try {
            str = dis.readUTF();
            if (str.equalsIgnoreCase("l")) {
                chat();
            }
            if (str.equalsIgnoreCase("w")) {
                file();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chat() {
        String info;
        try {
            while (true) {
                // 如果对方，即客户端没有说话，就会阻塞到这里，这里的阻塞不会影响其他线程
                info = dis.readUTF();
                System.out.println("对方说：" + info);
                if (info.equals("bye")) {
                    System.out.println("对方下线了");
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void file() {
        String fileName;
        try {
            String[] laugh = dis.readUTF().split("=====");
            fileName = laugh[1];
            File file = new File("F:\\" + fileName);
            OutputStream os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int i = 0;
            while ((i = dis.read(bytes)) != -1) {
                os.write(bytes, 0, i);
            }
            os.close();
            System.out.println(laugh[0] + "给你传输了文件" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
