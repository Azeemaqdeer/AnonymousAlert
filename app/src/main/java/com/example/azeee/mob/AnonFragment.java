package com.example.azeee.mob;

import androidx.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.azeee.mob.Notification.Token;
import com.example.azeee.mob.getanon.GetAnonPresenter;
import com.example.azeee.mob.getanon.GetanonContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AnonFragment extends Fragment implements GetanonContract.View, ItemClickSupport.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
public static final String ARG_TYPE = "type";
public static final String TYPE_CHATS = "type_chats";
public static final String TYPE_ALL = "type_all";

private SwipeRefreshLayout mSwipeRefreshLayout;
private RecyclerView mRecyclerViewAllUserListing;

private AnonListingRecyclerAdapter mUserListingRecyclerAdapter;

private GetAnonPresenter mGetUsersPresenter;

public static AnonFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        AnonFragment fragment = new AnonFragment();
        fragment.setArguments(args);
        return fragment;
        }

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_anonymous_list, container, false);
        bindViews(fragmentView);


        return fragmentView;

        }

private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = (RecyclerView) view.findViewById(R.id.recyclerview);
        //mRecyclerViewAllUserListing.setLayoutManager(new LinearLayoutManager(this));
        }

@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        }

private void init() {
        mGetUsersPresenter = new GetAnonPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
@Override
public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
        }
        });

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
        .setOnItemClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        }

@Override
public void onRefresh() {
        getUsers();
        }

private void getUsers() {
        if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_CHATS)) {

        } else if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_ALL)) {
        mGetUsersPresenter.getAllUsers();
        }
        }

@Override
public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        AnonChat.startActivity(getActivity(),
        mUserListingRecyclerAdapter.getUser(position).email,
        mUserListingRecyclerAdapter.getUser(position).uid,
        mUserListingRecyclerAdapter.getUser(position).name

        );
        }

@Override
public void onGetAllUsersSuccess(List<GetSet> users) {
        mSwipeRefreshLayout.post(new Runnable() {
@Override
public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
        }
        });
        mUserListingRecyclerAdapter = new AnonListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
        }

@Override
public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
@Override
public void run() {
        mSwipeRefreshLayout.setRefreshing(false);
        }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
        }

@Override
public void onGetChatUsersSuccess(List<GetSet> users) {

        }

@Override
public void onGetChatUsersFailure(String message) {

        }

        }


