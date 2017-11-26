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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController extends ManageClient implements Initializable {

    @FXML
    private TextField clientName;
    @FXML
    private PasswordField clientPassword;

    @FXML
    protected void handleFallbackAction(ActionEvent event) {
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
    protected void handleRegisterAction(ActionEvent event) {
        if (connDB.checkClientExist(clientName.getText())) {
            JOptionPane.showMessageDialog(null, "该账户已存在");
            clientPassword.setText("");
        } else {
            try {
                connDB.storeNewClient(clientName.getText(), clientPassword.getText());
            } catch (SQLException e) {
                e.printStackTrace();
                clientName.setText("");
                clientPassword.setText("");
                JOptionPane.showMessageDialog(null, "注册失败");
                return;
            }
            JOptionPane.showMessageDialog(null, "注册成功，返回登录界面");
            Login login = new Login();
            try {
                login.init();
                login.start(ClientStart.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
