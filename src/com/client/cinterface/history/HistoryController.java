package com.client.cinterface.history;

import com.client.ClientStart;
import com.client.ManageClient;
import com.client.cinterface.chat.Chat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HistoryController extends ManageClient implements Initializable {
    @FXML
    public ListView<String> historyMessageView;

    @FXML
    protected void handleBackToChatAction() {
        Chat chat = new Chat();
        whileList = true;
        try {
            chat.init();
            chat.start(ClientStart.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            out.writeUTF("@InitHistory@");
            out.flush();
            out.writeUTF(targetName);
            out.flush();
            ObservableList<String> historyList = FXCollections.observableArrayList();
            historyList.addAll(Arrays.asList(in.readUTF().split("@!@")));
            historyMessageView.setItems(historyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
