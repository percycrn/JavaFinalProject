package com.client.cinterface;

import com.client.cinterface.chat.Chat;
import com.client.cinterface.list.List;
import com.client.cinterface.login.Login;
import com.client.cinterface.register.Register;
import com.server.sinterface.Server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        List.launch();
    }
}
