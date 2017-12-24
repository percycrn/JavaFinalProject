package com.client.cinterface.login;

import com.client.ClientStart;
import com.client.ManageClient;
import com.client.cinterface.list.List;
import com.client.cinterface.register.Register;
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
import java.util.ResourceBundle;

public class LoginController extends ManageClient implements Initializable {
    @FXML
    public TextField clientNameLogin;
    @FXML
    public PasswordField password;

    @FXML
    protected void handleLoginAction() {
        if (clientNameLogin.getText().equals("") || password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "输入信息不能为空");
            return;
        }
        try {
            ManageClient.clientName = clientNameLogin.getText();
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("@Login@");
            out.flush();
            out.writeUTF(clientNameLogin.getText());
            out.flush();
            out.writeUTF(password.getText());
            out.flush();
            String message = in.readUTF();
            switch (message) {
                case "@ClientNotExist@":
                    JOptionPane.showMessageDialog(null, "can't find this account");
                    close();
                    break;
                case "@IncorrectPassword@":
                    JOptionPane.showMessageDialog(null, "incorrect password");
                    close();
                    break;
                case "@AlreadyLogin@":
                    JOptionPane.showMessageDialog(null,"account already login");
                    break;
                case "@SuccessLogin@":
                    clientName = in.readUTF();
                    List list = new List();
                    try {
                        list.init();
                        list.start(ClientStart.getStage());
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
        try {
            out.writeUTF("@Logout@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
