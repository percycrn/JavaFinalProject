package com.server.sinterface;

import com.server.ManageServer;
import com.server.util.ServerS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController extends ManageServer implements Initializable {
    @FXML
    public TextField port;
    public ListView listViewTop;
    public ListView listViewBottom;

    @FXML
    @SuppressWarnings("unchecked")
    protected void handleServerStartAction(ActionEvent event) {
        if (port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        PCPort = port.getText();
        port.setText("");
        new Thread(() -> {
            try {
                new ServerS(Integer.valueOf(PCPort));
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

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        listViewTop.setItems(getMessageTop());
        listViewBottom.setItems(getMessageBottom());
    }
}