package com.client.cinterface;

import com.client.util.ClientS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;


public class ClientController {
    public TextArea deliveredMessage;
    public TextField deliveringMessage;
    private String ClientName = "first";
    private ClientS clientS;
    @FXML
    private TextField host;
    @FXML
    private TextField port;

    @FXML
    protected void handleConnectAction(ActionEvent event) {
        if (host.getText().equals("") || port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        clientS = new ClientS(host.getText(), Integer.valueOf(port.getText()), ClientName);
    }

    @FXML
    protected void handleSendMessageAction(ActionEvent event) {
        if (deliveringMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "您的输入为空");
            return;
        }
        clientS.sendMessage(deliveringMessage.getText());
        deliveringMessage.setText("");
        deliveredMessage.setText(deliveredMessage.getText());
    }
}