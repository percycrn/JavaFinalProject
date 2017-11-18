package com.server.sinterface;

import com.server.ManageServer;
import com.server.util.ServerS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ServerController extends ManageServer {
    @FXML
    public ListView listView;
    @FXML
    public TextField port;

    @FXML
    @SuppressWarnings("unchecked")
    protected void handleServerStartAction(ActionEvent event) {
        if (port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        new Thread(() -> {
            try {
                listView.setItems(getClientMessage());
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
