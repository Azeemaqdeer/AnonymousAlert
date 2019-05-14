package com.example.azeee.mob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity4 extends AppCompatActivity {
  Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
     FirebaseUser firebaseUser;
    //  private LogoutPresenter mLogoutPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity4.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, MainActivity4.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lisiting);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayoutUserListing = (TabLayout) findViewById(R.id.tab_layout_user_listing);
        mViewPagerUserListing = (ViewPager) findViewById(R.id.view_pager_user_listing);
    }

    private void init() {
        // set the toolbar
       setSupportActionBar(mToolbar);
        try {

            // set the view pager adapter
            UserListingPagerAdapter userListingPagerAdapter = new UserListingPagerAdapter(getSupportFragmentManager());
            mViewPagerUserListing.setAdapter(userListingPagerAdapter);

            // attach tab layout with view pager
            mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);

            /// mLogoutPresenter = new LogoutPresenter(this);
        } catch (NullPointerException e) {
            Toast.makeText(this, "SOORY EXCEPTION", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delet, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.del:
                AlertDialog.Builder alter = new AlertDialog.Builder(MainActivity4.this);
                alter.setTitle("Are you sure");
                alter.setMessage("if you delete your annoymously login account your account will be permanently "+
                        " deleted ");
                alter.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(MainActivity4.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity4.this, MainActivity1.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                }
                                else{
                                    Toast.makeText(MainActivity4.this, "Account not deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
               alter.setNegativeButton("Dismis", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                   }
               });
               AlertDialog alertDialog = alter.create();
               alertDialog.show();
                return  true;


        }

        return false;

    }


}


