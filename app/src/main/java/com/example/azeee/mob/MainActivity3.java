package com.example.azeee.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
 Button  button5;
  MaterialEditText ed1;
 MaterialEditText ed2;
 MaterialEditText ed3;
  Context context;

   private FirebaseAuth fa;
   // MyFirebaseInstanceIDService   firebaseInstanceIDService;
   // public static final String ARG_FIREBASE_TOKEN = "firebaseToken";
 DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        fa = FirebaseAuth.getInstance();
        button5 = (Button)findViewById(R.id.button5);
        ed1 = (MaterialEditText) findViewById(R.id.ed1);
        ed2 = (MaterialEditText) findViewById(R.id.ed2);
        ed3= (MaterialEditText)findViewById(R.id.ed3);
        button5.setOnClickListener(this);
        fb = FirebaseDatabase.getInstance().getReference();
     //  prog = new ProgressDialog(this);
     context= getApplicationContext();
    }
    private  void registerUser(){
        String em = ed1.getText().toString().trim();
        String p = ed2.getText().toString().trim();
        String us = ed3.getText().toString().trim();
        if(TextUtils.isEmpty(em) || TextUtils.isEmpty(p) || TextUtils.isEmpty(us)){
            //email is empty
            Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show();
        }

        fa.createUserWithEmailAndPassword(em,p).addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                  add();
                    Toast.makeText(MainActivity3.this,"Register Sucessfully",Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(),AnonymousList.class));
                    //task.getResult().getUser();


                }
                else{
                    Toast.makeText(MainActivity3.this," not registed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void add() {
        String email = ed1.getText().toString().trim();
        String pas = ed2.getText().toString().trim();
        String nm = ed3.getText().toString().trim();
        FirebaseUser firebaseUser = fa.getCurrentUser();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        GetSet user = new GetSet(firebaseUser.getUid(),email,
                nm)
              ;
        database.child(Constants.ARG_USERS)
                .child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(MainActivity3.this,"User Added sucessfully",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity3.this,"unable to addd",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }







    @Override
    public void onClick(View v) {
         if(v == button5){
             registerUser();
         }

    }
}
