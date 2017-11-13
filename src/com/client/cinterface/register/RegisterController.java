package com.client.cinterface.register;

import com.client.cinterface.ClientStart;
import com.client.cinterface.login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class RegisterController{

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
        Login login = new Login();
        try {
            login.init();
            login.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
