package com.example.azeee.mob.getanon;

import com.example.azeee.mob.GetSet;
import com.example.azeee.mob.GetUsersContract;
import com.example.azeee.mob.GetUsersInteractor;

import java.util.List;

public class GetAnonPresenter implements GetanonContract.Presenter, GetanonContract.OnGetAllUsersListener {
    private GetanonContract.View mView;
    private GetanonInteractor mGetUsersInteractor;

    public GetAnonPresenter(GetanonContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetanonInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }

    @Override
    public void getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<GetSet> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }
}
