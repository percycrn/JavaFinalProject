package com.server.sinterface;

import com.server.ManageServer;
import com.server.util.ConnDB;
import com.server.util.ServerS;
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
    protected void handleServerStartAction() {
        if (port.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入有效信息");
            return;
        }
        PCPort = port.getText();
        port.setText("");
        ServerS serverS = new ServerS(Integer.valueOf(PCPort));
        serverS.startListener();
    }

    @FXML
    protected void handleCloseAction() {
        // TODO
        System.exit(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        listViewTop.setItems(messageTop);
        listViewBottom.setItems(messageBottom);
    }
}