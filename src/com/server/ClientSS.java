package com.server;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class ClientSS implements Runnable {
    private static HashMap<String, Socket> map = new HashMap<>();
    private String number = null;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientSS(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        getKey();
    }

    private String getNumber() {
        try {
            number = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    private void getKey() {
        map.put(getNumber(), socket); // 得到账号保存Map
        Set<String> keySet = map.keySet();
        try {
            out.writeUTF(keySet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String oth;
        try {
            while (true) {
                oth = in.readUTF();
                if (oth.equalsIgnoreCase("l")) {
                    getOtherNumber();
                }
                if (oth.equalsIgnoreCase("w")) {
                    getFileTransmission();
                }
                if (oth.equalsIgnoreCase("t")) {
                    in.close();
                    out.close();
                    map.remove(number);
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                asd.writeUTF(number + "=====" + fileName);
                asd.flush();
                byte[] b = new byte[1024];
                int i = 0;
                while ((i = in.read(b)) != -1) {
                    asd.write(b, 0, i);
                    asd.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getOtherNumber() {
        while (true) {
            try {
                System.out.println("hhhhhhhhh");
                String oth = in.readUTF();
                System.out.println(oth);
                Socket other = map.get(oth);
                DataOutputStream asd = new DataOutputStream(other.getOutputStream());
                if (oth.equalsIgnoreCase("bye")) {
                    return;
                }
                String value = in.readUTF();
                String str = number + "对你说：\r\n" + value;
                asd.writeUTF("l");
                asd.flush();
                asd.writeUTF(str);
                asd.flush();
                if (value.equalsIgnoreCase("bye")) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("聊天传输出错");
            }
        }
    }
}
/*public static void start() throws IOException {
        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.31.16",10086);
        //2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        pw.write("用户名：admin；密码：123");
        pw.flush();
        socket.shutdownOutput();
        //3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info;
        while ((info = br.readLine()) !=null){
            System.out.println("我是客户端，服务器说：" + info);
        }

        //4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }*/