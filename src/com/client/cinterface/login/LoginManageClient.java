package com.client.cinterface.login;

import com.client.cinterface.ClientStart;
import com.client.ManageClient;
import com.client.cinterface.list.List;
import com.client.cinterface.register.Register;
import com.client.util.ClientS;
import com.server.util.ConnDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class LoginManageClient extends ManageClient implements Initializable {
    @FXML
    public TextField clientNameLogin;
    @FXML
    public TextField password;

    @FXML
    protected void handleLoginAction() {
        if (host.getText().equals("") || port.getText().equals("") ||
                clientNameLogin.getText().equals("") || password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "输入信息不能为空");
            return;
        }
        try {
            socket = new Socket(host.getText(), Integer.valueOf(port.getText()));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("@Login@");
            out.flush();
            out.writeUTF(clientNameLogin.getText());
            out.flush();
            out.writeUTF(password.getText());
            out.flush();
            String loginStat = in.readUTF();
            switch (loginStat) {
                case "@ClientNotExist@":
                    JOptionPane.showMessageDialog(null, "账户未注册");
                    break;
                case "@IncorrectPassword@":
                    JOptionPane.showMessageDialog(null, "密码错误");
                    break;
                case "@SuccessLogin@":
                    try {
                        System.out.println(6);
                        try {
                            clientS = new ClientS(host.getText(), Integer.valueOf(port.getText()), clientNameLogin.getText());
                            System.out.println(7);
                            clientName = clientNameLogin.getText();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "连接错误，请重试");
                            return;
                        }
                        // turn to list interface
                        List list = new List();
                        try {
                            list.init();
                            list.start(ClientStart.getStage());
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "登录失败");
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleRegisterAction() {
        Register register = new Register();
        try {
            register.init();
            register.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleExitAction() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
