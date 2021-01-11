package com.example.eatit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.view.Menu;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        updateLogOptions();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new HomeFragment())
                .commitAllowingStateLoss();
        navigationView.getMenu().getItem(0).setChecked(true) ;
        setTitle("Home");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
              return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        CartHelper cartHelper = new CartHelper(MainActivity.this);
        int cartCount=cartHelper.getNumberOfItemInCart();

        if (cartCount > 0) {
            Drawable cartIcons= VectorDrawableCompat.create(getResources(),R.drawable.ic_add_shopping_cart_black_24dp,null);
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), cartIcons, ActionItemBadge.BadgeStyles.YELLOW , cartCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));

        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        updateLogOptions();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateLogOptions();
    }

    private  void updateLogOptions(){
        TextView tvName=navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        TextView tvEmail=navigationView.getHeaderView(0).findViewById(R.id.tv_user_email);
        MenuItem logoutItem=navigationView.getMenu().findItem(R.id.nav_logout);
        MenuItem loginItem=navigationView.getMenu().findItem(R.id.nav_login);
       // SharedPreferences sharedPrefs=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
     //   boolean isUserLoggrdIn=sharedPrefs.getBoolean("isUserLoggedIn",false);
        if (!SessionHelper.isUserLoggedIn(MainActivity.this)){
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
            tvName.setText(R.string.app_name);
            tvEmail.setText("Loving It");
        }else {
            logoutItem.setVisible(true);
            loginItem.setVisible(false);
            User userJson=SessionHelper.getCurrentUser(MainActivity.this);
           // if (userJson!=null){
             //   try {
             //       JSONObject jsonObject=new JSONObject(String.valueOf(userJson));
                    String name=userJson.getFullNmae();
                    String email=userJson.getEmail();
                    tvName.setText(name);
                    tvEmail.setText(email);

          //      } catch (Exception e) {
           //         e.printStackTrace();
           //     }
            }

        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id==R.id.action_cart){
            Intent intent=new Intent(MainActivity.this,CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,new HomeFragment())
                    .commitAllowingStateLoss();
            setTitle("Home");




        } else if (id == R.id.nav_slideshow) {
            setTitle("My Cart");
            Intent intent=new Intent(MainActivity.this,CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_tools) {
            setTitle("My Order");
            Intent intent=new Intent(MainActivity.this,OutActivity.class);
            startActivity(intent);

        }

         else if (id == R.id.nav_logout) {
           SessionHelper.logout(MainActivity.this);
           startActivity(new Intent(this,MainActivity.class));
            updateLogOptions();

        }else if (id==R.id.nav_login){
            Intent intent=new Intent(MainActivity.this,SigninActivity.class);
            intent.putExtra("destination","finish");
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateLogOptions();
    }

}
