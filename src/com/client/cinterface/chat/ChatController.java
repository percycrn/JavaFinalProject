package com.client.cinterface.chat;

import com.client.cinterface.Controller;
import com.client.cinterface.login.LoginController;
import com.client.util.ClientS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatController extends Controller {
    public TextField deliveringMessage;
    public TextField targetName;
    public TextArea otherMessage;

    @FXML
    protected void handleSendMessageAction(ActionEvent event) {
        if (deliveringMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "您的输入为空");
            return;
        }
        try {
            clientS.sendMessage(deliveringMessage.getText(), targetName.getText());
            deliveringMessage.setText("");
            // otherMessage.setText(otherMessage.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "发送失败，请重试");
        }

    }
}