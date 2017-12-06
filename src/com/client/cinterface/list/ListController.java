package com.client.cinterface.list;

import com.client.ManageClient;
import com.client.cinterface.ClientStart;
import com.client.cinterface.chat.Chat;
import com.client.cinterface.login.Login;
import com.server.util.ConnDB;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ListController extends ManageClient implements Initializable {

    @FXML
    public ListView<String> friendListView;
    @FXML
    public TextField friendName;

    @FXML
    protected void handleAddFriendAction() {
        if (checkFriendExist(friendName.getText())) {
            JOptionPane.showMessageDialog(null, "该用户已添加到列表中");
            return;
        }
        try {
            out.writeUTF("@AddFriend@");
            out.flush();
            out.writeUTF(friendName.getText());
            out.flush();
            String addFriendStat = in.readUTF();
            switch (addFriendStat) {
                case "@SuccessToAddFriend@":
                    friendList.add(friendName.getText());
                    break;
                case "@FriendNotExist@":
                    JOptionPane.showMessageDialog(null, "Friend client not exists");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleBackToLoginAction() {
        Login login = new Login();
        try {
            login.init();
            login.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!friendListInitFlag) {
            try {
                out.writeUTF("@InitFriendList@");
                out.flush();
                String[] initFriendList = in.readUTF().split(",");
                friendList.addAll(Arrays.asList(initFriendList));
            } catch (IOException e) {
                e.printStackTrace();
            }
            friendListInitFlag = true;
            clientS.receiveMessage();
        }
        friendListView.setItems(friendList);
        friendListView.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    targetName = friendListView.getSelectionModel().getSelectedItem().trim();
                    Chat chat = new Chat();
                    try {
                        chat.init();
                        chat.start(ClientStart.getStage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private boolean checkFriendExist(String friendName) {
        for (String aFriendList : friendList) {
            if (aFriendList.equals(friendName)) {
                return true;
            }
        }
        return false;
    }
}