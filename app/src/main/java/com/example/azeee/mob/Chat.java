package com.example.azeee.mob;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.azeee.mob.Fragment.APIService;
import com.example.azeee.mob.Notification.Client;
import com.example.azeee.mob.Notification.Data;
import com.example.azeee.mob.Notification.MyResponse;
import com.example.azeee.mob.Notification.Sender;
import com.example.azeee.mob.Notification.Token;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
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
    APIService apiService;

    List<GetSet> sender;
    boolean notify =false;
    public static void startActivity(Context context, String receiver, String receiverUid,String name){
        Intent intent= new Intent(context, Chat.class);
        intent.putExtra("recevier", receiver);
        intent.putExtra("id",receiverUid);
        intent.putExtra("name",name);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mToolbar = findViewById(R.id.toolbar);
        btn_send = (ImageButton)findViewById(R.id.btn_send);
        text_send =findViewById(R.id.text_send);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
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
        profile = findViewById(R.id.profile);
        user = findViewById(R.id.title);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        final  Intent intent = getIntent();
      final  String id = intent.getStringExtra("id");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    messageSend(firebaseUser.getUid(),id,msg);
                    // readMessage(firebaseUser.getUid(),id,);
                }
                else{

                    Toast.makeText(Chat.this,"no message",Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
        try {

            reference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
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

    private  void messageSend(String sender, final String recevire, String mesage){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",recevire);
        hashMap.put("mesage",mesage);

        reference.child("Chats").push().setValue(hashMap);
        updateToken(FirebaseInstanceId.getInstance().getToken());

         final String msg = mesage;
      DatabaseReference   refence2 = FirebaseDatabase.getInstance().getReference("ANONYMOUS").child(firebaseUser.getUid());
      refence2.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 GetSet getSet = dataSnapshot.getValue(GetSet.class);

                 if(notify){
                 sendNotification(recevire,getSet.getName(),msg);}
                 notify = false;
                 //  FirebaseUser f = FirebaseAuth.getInstance().getCurrentUser();
                 //anonListingRecyclerAdapter.d = getSet.getUid();
               ;
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }

         });
         DatabaseReference referenc = FirebaseDatabase.getInstance().getReference("ANONYMOUS").child(firebaseUser.getUid());
        referenc.child("name").setValue("New Message");


    }
    public void updateToken(String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Token");
        Token token1 = new Token(token);
        databaseReference.child(firebaseUser.getUid()).setValue(token1);

    }


    private void sendNotification(final String recevire,final  String name,  final String msg) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Token");
        Query query = tokens.orderByKey().equalTo(recevire);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(),R.mipmap.ic_launcher,name+": "+msg,"new message",recevire);
                    Sender sender  = new Sender(data,token.getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code() == 200){

                                if(response.body().success != 1){
                                    Toast.makeText(Chat.this," failed",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public  void readMessage(final String id) {
        // final Intent intent = getIntent();
        // final String id = intent.getStringExtra("id");
        chatMessages = new ArrayList<>();
       reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage cha = snapshot.getValue(ChatMessage.class);
                    if(cha.getReceiver().equals(id) && cha.getSender().equals(firebaseUser.getUid())   ||
                            cha.getReceiver().equals(firebaseUser.getUid()) && cha.getSender().equals(id)
                            ){
                        chatMessages.add(cha);
                    }
                    messageAdapter = new MessageAdapter(Chat.this,chatMessages);
                    recyclerView.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

