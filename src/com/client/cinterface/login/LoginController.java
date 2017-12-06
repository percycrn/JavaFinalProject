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

public class LoginController extends ManageClient implements Initializable {
    @FXML
    public TextField clientNameLogin;
    @FXML
    public TextField password;

    @FXML
    protected void handleLoginAction() {
        if (clientNameLogin.getText().equals("") || password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "输入信息不能为空");
            return;
        }
        try {
            clientS = new ClientS(host, port, clientNameLogin.getText());
            out.writeUTF("@Login@");
            out.flush();
            out.writeUTF(clientNameLogin.getText());
            out.flush();
            out.writeUTF(password.getText());
            out.flush();
            String registerStat = in.readUTF();
            switch (registerStat) {
                case "@ClientNotExist@":
                    JOptionPane.showMessageDialog(null, "账户未注册");
                    close();
                    break;
                case "@IncorrectPassword@":
                    JOptionPane.showMessageDialog(null, "密码错误");
                    close();
                    break;
                case "@SuccessLogin@":
                    clientName = clientNameLogin.getText();
                    List list = new List();
                    try {
                        list.init();
                        list.start(ClientStart.getStage());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "登录失败");
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

    private void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
}
