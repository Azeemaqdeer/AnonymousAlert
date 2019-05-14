package com.example.azeee.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.azeee.mob.Notification.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.zip.Inflater;

public class StartActivity extends AppCompatActivity {

   Button anonymous;
   FirebaseUser fuser;
   Spinner spinner;
    private FirebaseAuth mAuth;
    ArrayAdapter<String>  adapter;
    String per[] = {"Select your Module","ADMIN","ANONYMOUS"
};
    String record;
// ...
// Initialize Firebase Auth
// password of admins are 1234567 / 123456
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
         spinner = (Spinner)findViewById(R.id.spinner);
        // FirebaseUser firebaseUser = mAuth.getCurrentUser();
        adapter = new ArrayAdapter<String>(StartActivity.this,android.R.layout.simple_spinner_item,per);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        anonymous = (Button) findViewById(R.id.anonymous);


              spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     switch (position){
                         case 0:
                         record = "Select your Module";
                         break;
                         case 1:
                         record = "admin";
                         break;
                         case 2:
                      record = "Anonymous";
                      break;

                     }

                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {

                  }
              });
if(fuser == null){
 anonymous.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         if(record == "Anonymous"){
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    add();
                    Toast.makeText(StartActivity.this,"ANONYMOUS",Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(StartActivity.this,MainActivity.class));
                }
            });
         }else if(record == "admin")
         {
             startActivity(new Intent(StartActivity.this,MainActivity2.class));
         }else{
             AlertDialog.Builder al = new AlertDialog.Builder(StartActivity.this);
             al.setMessage("Dear user please select your login module");
             al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     startActivity(new Intent(StartActivity.this,StartActivity.class));
                 }
             });

             AlertDialog alertDialog = al.create();
             alertDialog.show();
         }
     }
 });



}else{
    if(fuser.isAnonymous()){
        startActivity(new Intent(StartActivity.this,MainActivity.class));

    }else{
        startActivity(new Intent(StartActivity.this,AnonymousList.class));
    }

}}


    private void add() {
            //  String email = ed1.getText().toString().trim();
            // String pas = ed2.getText().toString().trim();
            // String nm = ed3.getText().toString().trim();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();

            DatabaseReference abase = FirebaseDatabase.getInstance().getReference();
            GetSet user = new GetSet(firebaseUser.getUid(), "Anonymous"
            );
            //HashMap<String ,String> hashMap = new HashMap<>();
            //  hashMap.put("username",nm);
            abase.child("ANONYMOUS")
                    .child(firebaseUser.getUid())
                    .setValue(user)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StartActivity.this, "User Added sucessfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StartActivity.this, "unable to addd", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



}



