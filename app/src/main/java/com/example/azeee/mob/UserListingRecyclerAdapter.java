package com.example.azeee.mob;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {
    private List<GetSet> mUsers;
    public UserListingRecyclerAdapter(List<GetSet> users) {
        this.mUsers = users;
    }
    public void add(GetSet user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    @NonNull
    @Override
    public UserListingRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new UserListingRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListingRecyclerAdapter.ViewHolder holder, int position) {
        GetSet user = mUsers.get(position);

        if (user.name != null){
            // String alphabet = user.name.substring(0, 1);

            holder.txtUsername.setText(user.getName());
            holder.imageView.setImageResource(R.drawable.imag);
        }
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }
    public GetSet getUser(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  txtUsername;
        CircleImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.profile);
            txtUsername = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
