package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity {
    androidx.cardview.widget.CardView mCurrentConsInfo,mStoreItem
           ,mCustRequest,mhomePageConstruction,mAddInteriorDesign;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.adb_toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        mCurrentConsInfo = (androidx.cardview.widget.CardView)findViewById(R.id.ongoing_construction_info);
        mCurrentConsInfo.setBackgroundResource(R.drawable.service_card_view_background3);


     


        mStoreItem = (androidx.cardview.widget.CardView)findViewById(R.id.store_item);
        mStoreItem.setBackgroundResource(R.drawable.service_card_view_background1);


      


        mCustRequest = (androidx.cardview.widget.CardView)findViewById(R.id.customer_requests);
        mCustRequest.setBackgroundResource(R.drawable.service_card_view_background4);

       // mhomePageConstruction = (androidx.cardview.widget.CardView)findViewById(R.id.best_construction);
       // mhomePageConstruction.setBackgroundResource(R.drawable.service_card_view_background3);

        mAddInteriorDesign = (androidx.cardview.widget.CardView)findViewById(R.id.add_interior_design);
        mAddInteriorDesign.setBackgroundResource(R.drawable.service_card_view_background1);



        mCurrentConsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,CurrentConstInfo.class);
                intent.putExtra("key","Current Construction Work");
                startActivity(intent);
            }
        });
      
       
        mStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,AdminStoreItem.class);
                startActivity(intent);
            }
        });
       
        mCustRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,CustomersRequests.class);
                startActivity(intent);
            }
        });

    /*    mhomePageConstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,CurrentConstInfo.class);
                intent.putExtra("key","Best Construction Work");
                startActivity(intent);
            }
        });*/
        mAddInteriorDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,CurrentConstInfo.class);
                intent.putExtra("key","Interior Design");
                startActivity(intent);
            }
        });



    }

}