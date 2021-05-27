package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CurrentConstInfo extends AppCompatActivity {
    androidx.recyclerview.widget.RecyclerView mAdminConsRecView;

    androidx.appcompat.widget.Toolbar adminToolbar;

    MyFirebaseRecyclerAdapter adapter;
    String s;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu_for_current_cons_info,menu);


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_const_info);

        adminToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar_admin_current_cons);
       setSupportActionBar(adminToolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAdminConsRecView = (androidx.recyclerview.widget.RecyclerView)findViewById(R.id.admin_current_cons_rec_view);
        mAdminConsRecView.setLayoutManager(new LinearLayoutManager(CurrentConstInfo.this,LinearLayoutManager.VERTICAL,false));
        s = getIntent().getStringExtra("key");
        FirebaseRecyclerOptions<ModelDataForCurrentCons> options =
                new FirebaseRecyclerOptions.Builder<ModelDataForCurrentCons>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(s),ModelDataForCurrentCons.class)
                        .build();


         adapter = new MyFirebaseRecyclerAdapter(options,CurrentConstInfo.this,0,s);
        mAdminConsRecView.setAdapter(adapter);
        adapter.notifyDataSetChanged();







    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.add_fire_base_Current_cons_info:
                Intent intent = new Intent(CurrentConstInfo.this,AddFireBaseDataForCurrentConsInfo.class);
                intent.putExtra("key",s);
                startActivity(intent);
                //Toast.makeText(this, "hurray", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }
}