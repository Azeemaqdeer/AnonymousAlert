package com.example.azeee.mob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azeee.mob.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AnonChat extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mToolbar;
    MessageAdapter messageAdapter;
    List<ChatMessage> chatMessages;
    RecyclerView recyclerView;
    CircleImageView profile;
    TextView user;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    EditText text_send;
    ImageButton btn_send;

    boolean notify =false;
    public static void startActivity(Context context, String receiver, String receiverUid, String name){
        Intent intent= new Intent(context, AnonChat.class);
        intent.putExtra("recevier", receiver);
        intent.putExtra("id",receiverUid);
        intent.putExtra("name",name);

        context.startActivity(intent);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anon_chat);
        mToolbar = findViewById(R.id.toolbar);
        text_send =findViewById(R.id.text_send);

        btn_send = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
   setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent inte = getIntent();

        profile = findViewById(R.id.profile);
        user = findViewById(R.id.title);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    messageSend(firebaseUser.getUid(),id,msg);
                    // readMessage(firebaseUser.getUid(),id,);
                }
                else{

                    Toast.makeText(AnonChat.this,"no message",Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        try {

            reference = FirebaseDatabase.getInstance().getReference().child("ANONYMOUS").child(id);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GetSet getSet = dataSnapshot.getValue(GetSet.class);
                    //String id = intent.getStringExtra("id");
                    // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    profile.setImageResource(R.mipmap.ic_launcher);

                    user.setText(getSet.getName());
                    readMessage(id);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }  catch(NullPointerException e ){}

    }

    private  void messageSend(final String sender, final String recevire, final String mesage){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",recevire);
        hashMap.put("mesage",mesage);
        // hashMap.put("isseen",false);

        reference.child("Chats").push().setValue(hashMap);
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference().child("ANONYMOUS").child(recevire);
        refer.child("name").setValue("Anonymous");
    }




    public  void readMessage(final String id) {
        // final Intent intent = getIntent();
        // final String id = intent.getStringExtra("id");
        chatMessages = new ArrayList<>();
       reference= FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  chatMessages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage cha = snapshot.getValue(ChatMessage.class);
                    if(cha.getReceiver().equals(firebaseUser.getUid()) && cha.getSender().equals(id)   ||
                            cha.getReceiver().equals(id) && cha.getSender().equals(firebaseUser.getUid())
                            ){
                        chatMessages.add(cha);
                    }
                    messageAdapter = new MessageAdapter(AnonChat.this,chatMessages);
                    recyclerView.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
