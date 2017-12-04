package com.client.cinterface.set;

import com.client.ManageClient;
import com.client.cinterface.ClientStart;
import com.client.cinterface.login.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SetHostPortController extends ManageClient implements Initializable {
    @FXML
    public TextField host;
    @FXML
    public TextField port;

    @FXML
    protected void handleSetHostPortAction() {
        try {
            socket = new Socket(host.getText(), Integer.valueOf(port.getText()));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("@Set@");
            out.flush();
            if (in.readUTF().equals("@AbleToConnect@")) {
                ManageClient.host = host.getText();
                ManageClient.port = Integer.valueOf(port.getText());
                Login login = new Login();
                try {
                    login.init();
                    login.start(ClientStart.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(ManageClient.host);
                System.out.println(ManageClient.port);
            } else {
                host.setText("");
                port.setText("");
                JOptionPane.showMessageDialog(null, "fail to connect to server");
            }
        } catch (IOException e) {
            host.setText("");
            port.setText("");
            JOptionPane.showMessageDialog(null, "fail to connect to server");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
