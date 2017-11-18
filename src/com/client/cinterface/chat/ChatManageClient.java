package com.client.cinterface.chat;

import com.client.ManageClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ChatManageClient extends ManageClient {
    public TextField deliveringMessage;
    public TextField targetName;
    public ListView leftMessageView;
    public ListView rightMessageView;

    @FXML
    @SuppressWarnings("unchecked")
    protected void handleSendMessageAction(ActionEvent event) {
        leftMessageView.setItems(getLeftMessage());
        rightMessageView.setItems(getRightMessage());
        if (deliveringMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "您的输入为空");
            return;
        }
        getRightMessage().add(deliveringMessage.getText()); // 在聊天左界面增加内容
        getLeftMessage().add(" ");
        try {
            getClientS().sendMessage(deliveringMessage.getText(), targetName.getText()); // 发送信息
            deliveringMessage.setText(""); // 清空text框
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "发送信息失败");
        }
    }
}