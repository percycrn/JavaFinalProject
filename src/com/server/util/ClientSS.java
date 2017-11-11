package com.server.util;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class ClientSS implements Runnable {
    private static HashMap<String, Socket> map = new HashMap<>();
    private String clientName = null;
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
        storeClientAccount();
    }

    private void storeClientAccount() {
        try {
            clientName = in.readUTF();
            map.put(clientName,socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMapElement() {
        Set<String> keySet = map.keySet();
        try {
            out.writeUTF(keySet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String clientMessage;
        try {
            while (true) {
                clientMessage = in.readUTF();
                if (clientMessage.equalsIgnoreCase("l")) {
                    chat();
                }
                /*if (clientMessage.equalsIgnoreCase("w")) {
                    getFileTransmission();
                }*/
                if (clientMessage.equalsIgnoreCase("t")) {
                    in.close();
                    out.close();
                    map.remove(clientName);
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chat() throws IOException {
        System.out.println("start chatting");
        out=new DataOutputStream(map.get(clientName).getOutputStream());
        while (true) {
            try {
                /*String oth = in.readUTF();
                System.out.println(oth);
                DataOutputStream asd = new DataOutputStream(map.get(clientName).getOutputStream());
                if (oth.equalsIgnoreCase("bye")) {
                    return;
                }*/
                //String value = in.readUTF();
                //String str = clientName + "对你说：\r\n" + value;
                System.out.println(clientName + "对你说：\r\n" + in.readUTF());
                /*asd.writeUTF("l");
                asd.flush();*/
                //out.writeUTF(str);
                //out.flush();
                //if (value.equalsIgnoreCase("bye")) {
                //    return;
                //}
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("聊天传输出错");
                break;
            }
        }
    }

/*
    private void getFileTransmission() {
        while (true) {
            try {
                String oth = in.readUTF();
                if (oth.equalsIgnoreCase("bye")) {
                    return;
                }
                Socket other = map.get(oth);
                String fileName = in.readUTF();
                DataOutputStream asd = new DataOutputStream(other.getOutputStream());
                asd.writeUTF("w");
                asd.flush();
                asd.writeUTF(clientName+ "=====" + fileName);
                asd.flush();
                byte[] b = new byte[1024];
                int i;
                while ((i = in.read(b)) != -1) {
                    asd.write(b, 0, i);
                    asd.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
}