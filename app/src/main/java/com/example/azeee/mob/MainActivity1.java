package com.example.azeee.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
       Thread thr = new Thread(){
           @Override
           public void run() {
               try {
                   sleep(3000);
                   Intent tn = new Intent(MainActivity1.this,StartActivity.class);
                   startActivity(tn);
                   finish();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }; thr.start();

    }
}
