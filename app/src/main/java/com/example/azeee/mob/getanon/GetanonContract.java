package com.example.azeee.mob.getanon;

import com.example.azeee.mob.GetSet;

import java.util.List;

public class GetanonContract {
    public interface View {
        void onGetAllUsersSuccess(List<GetSet> users);

        void onGetAllUsersFailure(String message);

        void onGetChatUsersSuccess(List<GetSet> users);

        void onGetChatUsersFailure(String message);
    }

    interface Presenter {
        void getAllUsers();

        void getChatUsers();
    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();
    }

    interface OnGetAllUsersListener {
        void onGetAllUsersSuccess(List<GetSet> users);

        void onGetAllUsersFailure(String message);
    }

    interface OnGetChatUsersListener {
        void onGetChatUsersSuccess(List<GetSet> users);

        void onGetChatUsersFailure(String message);
    }
}


