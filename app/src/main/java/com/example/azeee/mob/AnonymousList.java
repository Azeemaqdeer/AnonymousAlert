package com.example.azeee.mob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.azeee.mob.Notification.Token;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class AnonymousList extends AppCompatActivity {

    Toolbar mToolbar;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;



    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnonymousList.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, AnonymousList.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anon_lisiting);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(mToolbar);


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

        try {

             //set the view pager adapter
            AnonListingPagerAdapter anonListingPagerAdapter = new AnonListingPagerAdapter(getSupportFragmentManager());
            mViewPagerUserListing.setAdapter(anonListingPagerAdapter);

           // attach tab layout with view pager

            mTabLayoutUserListing.setupWithViewPager(mViewPagerUserListing);

            /// mLogoutPresenter = new LogoutPresenter(this);
        } catch (NullPointerException e) {
            Toast.makeText(this, "SOORY EXCEPTION", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()){
              case R.id.men:
            FirebaseAuth.getInstance().signOut();
                  Intent intent = new Intent(AnonymousList.this,StartActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(intent);
                  finish();
                  break;
              case R.id.add:
                  Intent intent1 = new Intent(AnonymousList.this,MainActivity3.class);
                  startActivity(intent1);
                  break;

        }

        return false;

    }

}




