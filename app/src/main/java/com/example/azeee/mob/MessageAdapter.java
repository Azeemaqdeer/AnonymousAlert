package com.example.azeee.mob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_LEFT = 1;
    public static final int MSG_RIGHT = 0;
    private Context mcontext;
    private List<ChatMessage> mUsers;
    FirebaseUser firebaseUser;

    private String imageUrl;

    public MessageAdapter(Context mcontext, List<ChatMessage> mUsers) {
        this.mcontext = mcontext;
        this.mUsers = mUsers;
        //this.imageUrl=imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT) {
            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_right_items, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else if(viewType == MSG_LEFT) {

            View view = LayoutInflater.from(mcontext).inflate(R.layout.chat_left_items, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final ChatMessage chatMessage = mUsers.get(position);
        holder.show_message.setText(chatMessage.getMesage());

        holder.profile.setImageResource(R.mipmap.ic_launcher);


    }
    @Override
    public int getItemCount() {
     return   mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public CircleImageView profile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile=itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUsers.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_RIGHT;
        }
        else {return MSG_LEFT;}
    }
}
