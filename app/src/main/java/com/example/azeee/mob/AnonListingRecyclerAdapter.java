package com.example.azeee.mob;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azeee.mob.UserListingRecyclerAdapter.ViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.GET;

public class AnonListingRecyclerAdapter extends RecyclerView.Adapter<AnonListingRecyclerAdapter.ViewHolder> {
    private List<GetSet> mUsers;
   public boolean send;
    public String d;
    public AnonListingRecyclerAdapter(boolean send,String d) {
      this.send = send;
      this.d = d;
    }


    public AnonListingRecyclerAdapter(List<GetSet> users) {
        this.mUsers = users;
    }

    public void add(GetSet user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anonymous_cardview, parent, false);
        return new AnonListingRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GetSet user = mUsers.get(position);


        holder.txtUsername.setText(user.getName());
        holder.imageView.setImageResource(R.drawable.anon);

            holder.count.setText(user.getCount());

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
        TextView txtUsername;
        CircleImageView imageView;
        TextView count;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.profile);
            txtUsername = (TextView) itemView.findViewById(R.id.title);
            count = (TextView) itemView.findViewById(R.id.count);
        }
    }


}