package com.example.azeee.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azeee.mob.Notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    private  Button button2;
  private   EditText ed3;
   private EditText ed4;
    private Button button;
    private  ProgressDialog prog ;
    private FirebaseAuth fba;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        prog = new ProgressDialog(this);
        fba = FirebaseAuth.getInstance();
        ed3 = (EditText)findViewById(R.id.ed3);
        ed4 = (EditText)findViewById(R.id.ed4);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    userLogin();
                }
            }
        });

    }
    private void userLogin(){
        String email = ed3.getText().toString().trim();
        String pasword = ed4.getText().toString().trim();
            if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pasword)){                        //email is empty
                Toast.makeText(this,"please enter missing field",Toast.LENGTH_SHORT).show();
            }
            else {

                fba.signInWithEmailAndPassword(email, pasword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            updateToken(FirebaseInstanceId.getInstance().getToken());
                            //start profile
                            finish();

                            startActivity(new Intent(getApplicationContext(), AnonymousList.class));
                           // updateToken(FirebaseInstanceId.getInstance().getToken());

                        } else {
                            Toast.makeText(MainActivity2.this, "cannot login", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

    }

    @Override
    public void onClick(View v) {

    }
    public void updateToken(String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Token");
        Token token1 = new Token(token);
        databaseReference.child(firebaseUser.getUid()).setValue(token1);

    }


}
