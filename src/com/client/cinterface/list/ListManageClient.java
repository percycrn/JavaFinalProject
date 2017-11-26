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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListManageClient extends ManageClient implements Initializable {

    @FXML
    public ListView<String> friendListView;
    @FXML
    public TextField friendName;

    @FXML
    protected void handleAddFriendAction(ActionEvent event) {
        if (connDB.checkClientExist(friendName.getText())) {
            friendList.add(friendName.getText());
            try {
                connDB.addFriendToDB(clientName, friendName.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "该账户不存在");
        }
    }

    @FXML
    protected void handleBackToLoginAction(ActionEvent event) {
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
        // TODO get friend message from server
        if (checkFriendExist(friendName.getText())){
            JOptionPane.showMessageDialog(null,"该用户已添加到列表中");
            friendName.setText("");
            return;
        }
        if (!friendListInitFlag) {
            try {
                connDB.initFriendList(friendList, clientName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            friendListInitFlag = true;
        }
        friendListView.setItems(friendList);
        friendListView.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    targetName = (String.valueOf(friendListView.getSelectionModel().selectedItemProperty()));
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