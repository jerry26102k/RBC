package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   static Fragment fragment = null;


    FrameLayout frameLayout;
    static Context context ;

    NavigationView mNav;
    ActionBarDrawerToggle mToggle;
    androidx.appcompat.widget.Toolbar mToolbar;

    DrawerLayout mDrawer;
    public static ArrayList<ModelDataForCurrentCons> data;
    DatabaseReference reference;

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);

        }else if(fragment instanceof HomeFragment){
                super.onBackPressed();


        }else {
            fragment = new HomeFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout)findViewById(R.id.fragment_container);
        mNav = (NavigationView)findViewById(R.id.main_navigation_view);
       context =  frameLayout.getContext();

      if(savedInstanceState == null) {
          getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
          mNav.setCheckedItem(R.id.drawable_menu_home);
      }






        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        mToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this,mDrawer,mToolbar,R.string.open,R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();



        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Intent intent;



                switch(item.getItemId()){
                    case R.id.drawable_menu_home:
                         fragment = new HomeFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();



                        break;
                    case R.id.drawable_menu_construction:
                        fragment = new ConstructionFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

                        break;
                    case R.id.drawable_menu_store:
                    fragment = new StoreFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                        break;
                    case R.id.drawable_interior_design:
                        fragment = new InteriorDesignFragment();
                      getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

                        break;

                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }





}