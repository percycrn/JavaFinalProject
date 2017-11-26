package com.client.cinterface.login;

import com.client.cinterface.ClientStart;
import com.client.ManageClient;
import com.client.cinterface.list.List;
import com.client.cinterface.register.Register;
import com.client.util.ClientS;
import com.server.util.ConnDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginManageClient extends ManageClient implements Initializable {

    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField clientNameLogin;
    @FXML
    public TextField password;

    @FXML
    protected void handleLoginAction(ActionEvent event) {
        if (host.getText().equals("") || port.getText().equals("") ||
                clientNameLogin.getText().equals("") || password.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "输入信息不能为空");
            return;
        }
        // check whether the client exists
        if (!connDB.checkClientExist(clientNameLogin.getText())) {
            JOptionPane.showMessageDialog(null, "该账户未注册");
            return;
        }
        // check clientName and corresponding password
        try {
            if (connDB.checkPassword(clientNameLogin.getText()).trim().equals(password.getText())) {
                // there's a trim here because I set password in DB nchar(20), so system will fill the attribute with blank.
                // if it is correct, create a clientS then turn to list interface
                try {
                    clientS = new ClientS(host.getText(), Integer.valueOf(port.getText()), clientNameLogin.getText());
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
            } else {
                JOptionPane.showMessageDialog(null, "密码不正确");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleRegisterAction(ActionEvent event) {
        Register register = new Register();
        try {
            register.init();
            register.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
