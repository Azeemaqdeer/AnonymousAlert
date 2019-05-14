package com.example.azeee.mob;

import java.util.List;

public interface GetUsersContract {
    interface View {
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
