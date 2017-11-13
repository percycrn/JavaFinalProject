package com.server.sinterface;

import com.server.util.ServerS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ServerController {
    @FXML
    public TextArea message;
    @FXML
    public TextField port;

    @FXML
    protected void handleServerStartAction(ActionEvent event) {
        if (port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        new Thread(() -> {
            try {
                new ServerS(Integer.valueOf(port.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "服务器创建失败，请更改port再尝试");
            }
        }).start();
    }

    @FXML
    protected void handleCloseAction(ActionEvent event) {
        // TODO
        System.exit(0);
    }
}
