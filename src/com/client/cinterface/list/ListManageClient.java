package com.client.cinterface.list;

import com.client.ManageClient;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;

public class ListManageClient extends ManageClient {

    public ListView<String> friendList;

    ListManageClient() {
        init();

    }

    private void init() {

    }

    protected void chatWithFriend() {
        friendList.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    // setTargetName();
                });
    }
}
