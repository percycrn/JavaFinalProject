package com.server.sinterface;

import com.server.util.ServerS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ServerController {
    public TextArea message;
    public TextField port;
    private ServerS serverS;

    @FXML
    protected void handleServerStartAction(ActionEvent event) {
        if (port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        serverS = new ServerS(Integer.valueOf(port.getText()));
    }
}
