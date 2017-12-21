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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController extends ManageClient implements Initializable {
    public TextField deliveringMessage;
    public ListView leftMessageView;
    public ListView rightMessageView;
    public Label currentFriendName;
    private Thread thread;

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
        System.out.println(1);
        try {
            stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(2);
        List list = new List();
        try {
            list.init();
            list.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleHistoryAction() {

    }

    @FXML
    protected void handleDeleteAction() {
        int n = JOptionPane.showConfirmDialog(null,
                "are you sure to delete this friend", "confirm", JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            try {
                out.writeUTF("@DeleteFriend@");
                out.flush();
                out.writeUTF(targetName);
                out.flush();
                friendList.remove(targetName);
                stop();
                List list = new List();
                list.init();
                list.start(ClientStart.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleExitAction() {
        clientS.exit();
        System.exit(0);
    }

    private synchronized void stop() throws InterruptedException {
        thread.wait();
    }

    private synchronized void start() {
        thread.notify();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        leftMessageView.setItems(leftMessage);
        rightMessageView.setItems(rightMessage);
        currentFriendName.setText(targetName);
        if (!threadFlag) {
            thread = new Thread(() -> clientS.receiveMessage());
            thread.start();
            threadFlag = true;
        } else {
            start();
        }
    }
}