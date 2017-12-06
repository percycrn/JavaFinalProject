package com.client.cinterface.register;

import com.client.ManageClient;
import com.client.cinterface.ClientStart;
import com.client.cinterface.login.Login;
import com.server.util.ConnDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController extends ManageClient implements Initializable {

    @FXML
    private TextField clientName;
    @FXML
    private PasswordField password;

    @FXML
    protected void handleFallbackAction() {
        Login login = new Login();
        try {
            login.init();
            login.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "fail to fallback to Login interface");
        }
    }

    @FXML
    protected void handleRegisterAction() {
        if (clientName.getText().equals("") || password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "输入信息不能为空");
            return;
        }
        try {
            Socket socket = new Socket(host, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("@Register@");
            out.flush();
            out.writeUTF(clientName.getText());
            out.flush();
            out.writeUTF(password.getText());
            out.flush();
            String loginStat = in.readUTF();
            if (loginStat.equals("@SuccessToRegister@")) {
                JOptionPane.showMessageDialog(null, "注册成功，返回登录界面");
                Login login = new Login();
                try {
                    login.init();
                    login.start(ClientStart.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (loginStat.equals("@ClientAlreadyExist@")) {
                clientName.setText("");
                password.setText("");
                JOptionPane.showMessageDialog(null, "Client already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}