package com.server.util;

import javax.swing.*;
import java.net.*;

public class ServerS {
    private static ServerSocket serverSocket;

    public ServerS(int port) {
        try {
            serverSocket = new ServerSocket(port);
            JOptionPane.showMessageDialog(null, "创建服务器成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            listener();
        }
    }

    private void listener() {
        try {
            Socket socket = serverSocket.accept();
            new Thread(new ClientSS(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
       *基于TCP协议的Socket通信，实现用户登录，服务端

        //3、获取输入流，并读取客户端信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info;
        while ((info = br.readLine()) != null) {
            JOptionPane.showMessageDialog(null, info);
            System.out.println("我是服务器，客户端说：" + info);
        }

    }

    public static void ServerSocketEnd() throws IOException {
        socket.shutdownInput(); // 关闭输入流

        //4、获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("欢迎您！");
        pw.flush();

        //5、关闭资源
        pw.close();
        os.close();
        br.close();
        is.close();
        socket.close();
        serverSocket.close();
    }*/