package com.client.cinterface.chat;

import com.client.ManageClient;
import com.client.ClientStart;
import com.client.cinterface.history.History;
import com.client.cinterface.list.List;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
        rightMessage.add(deliveringMessage.getText());
        leftMessage.add(" ");
        try {
            try {
                out.writeUTF("@SendMessage@");
                out.flush();
                out.writeUTF(ManageClient.targetName);
                out.flush();
                out.writeUTF(deliveringMessage.getText());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            deliveringMessage.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "发送信息失败");
        }
    }

    @FXML
    protected void handleBackToListAction() {
        try {
            out.writeUTF("@StopWhile@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List list = new List();
        try {
            list.init();
            list.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleDeleteAction() {
        int n = JOptionPane.showConfirmDialog(null,
                "are you sure to delete this friend", "confirm", JOptionPane.YES_NO_OPTION);
        System.out.println(n);
        if (n == 0) {
            try {
                out.writeUTF("@DeleteFriend@");
                out.flush();
                out.writeUTF(targetName);
                out.flush();
                List list = new List();
                list.init();
                list.start(ClientStart.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleHistoryAction() {
        try {
            out.writeUTF("@StopWhile@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        History history = new History();
        try {
            history.init();
            history.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleExitAction() {
        try {
            out.writeUTF("@Logout@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        leftMessageView.setItems(leftMessage);
        rightMessageView.setItems(rightMessage);
        currentFriendName.setText(targetName);
        new Thread(() -> {
            while (whileList) {
                try {
                    String message = in.readUTF();
                    switch (message) {
                        case "@SuccessToDeleteFriend@":
                            whileList = false;
                            break;
                        case "@FailToDeleteFriend@":
                            JOptionPane.showMessageDialog(null, "fail to delete this friend");
                            break;
                        case "@NewMessage@":
                            String newMessage = in.readUTF();
                            if (!newMessage.equals("")) {
                                leftMessage.add(newMessage);
                                rightMessage.add(" ");
                            }
                            break;
                        case "@StopWhile@":
                            whileList = false;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}