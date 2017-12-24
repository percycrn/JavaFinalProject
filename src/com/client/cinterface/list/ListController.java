package com.client.cinterface.list;

import com.client.ManageClient;
import com.client.ClientStart;
import com.client.cinterface.chat.Chat;
import com.client.cinterface.login.Login;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ListController extends ManageClient implements Initializable {

    @FXML
    public ListView<String> friendListView;
    @FXML
    public TextField friendName;

    @FXML
    protected void handleAddFriendAction() {
        if (friendName.getText().equals(clientName)) {
            JOptionPane.showMessageDialog(null, "Cannot add yourself");
            return;
        }
        if (checkFriendExist(friendName.getText())) {
            JOptionPane.showMessageDialog(null, "You have already add this friend");
            return;
        }
        try {
            out.writeUTF("@AddFriend@");
            out.flush();
            out.writeUTF(friendName.getText());
            out.flush();
            String message = in.readUTF();
            switch (message) {
                case "@SuccessToAddFriend@":
                    friendList.add(in.readUTF());
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
        try {
            out.writeUTF("@Logout@");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Login login = new Login();
        try {
            login.init();
            login.start(ClientStart.getStage());
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
    public void initialize(URL location, ResourceBundle resources) {
        try {
            friendList.remove(0, friendList.size());
            out.writeUTF("@InitFriendList@");
            out.flush();
            friendList.addAll(Arrays.asList(in.readUTF().split(",")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        friendListView.setItems(friendList);
        friendListView.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    targetName = friendListView.getSelectionModel().getSelectedItem().trim();
                    Chat chat = new Chat();
                    whileList = true;
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