package com.client.cinterface.login;

import com.client.cinterface.ClientStart;
import com.client.cinterface.Controller;
import com.client.cinterface.chat.Chat;
import com.client.cinterface.register.Register;
import com.client.util.ClientS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller{
    public TextField password;
    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField clientName;

    @FXML
    protected void handleLoginAction(ActionEvent event) {
        if (host.getText().equals("") || port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        try {
            clientS = new ClientS(host.getText(), Integer.valueOf(port.getText()), clientName.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "连接错误，请重试");
        }
        Chat chat = new Chat();
        try {
            chat.init();
            chat.start(ClientStart.getStage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "登录失败");
            e.printStackTrace();
        }
    }

    @FXML protected void handleRegisterAction(ActionEvent event){
        Register register=new Register();
        try {
            register.init();
            register.start(ClientStart.getStage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
