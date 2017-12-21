package com.client.cinterface.chat;

import com.client.ManageClient;
import com.client.ClientStart;
import com.client.cinterface.list.List;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController extends ManageClient implements Initializable {
    public TextField deliveringMessage;
    public ListView leftMessageView;
    public ListView rightMessageView;
    public Label currentFriendName;

    @FXML
    @SuppressWarnings("unchecked")
    protected void handleSendMessageAction() {
        if (deliveringMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "您的输入为空");
            return;
        }
        rightMessage.add(deliveringMessage.getText()); // 在聊天左界面增加内容
        leftMessage.add(" ");
        try {
            clientS.sendMessage(deliveringMessage.getText()); // 发送信息
            deliveringMessage.setText(""); // 清空text框
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "发送信息失败");
        }
    }

    @FXML
    protected void handleBackToListAction() {
        List list = new List();
        try {
            list.init();
            list.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        leftMessageView.setItems(leftMessage);
        rightMessageView.setItems(rightMessage);
        currentFriendName.setText(targetName);
        //clientS.receiveMessage();
    }
}