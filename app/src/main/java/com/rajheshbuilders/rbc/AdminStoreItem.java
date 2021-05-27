package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminStoreItem extends AppCompatActivity {

    androidx.recyclerview.widget.RecyclerView mAdminStoreItemRecView;

    androidx.appcompat.widget.Toolbar adminStoreItemToolbar;

    StoreFireBaseAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store_item);



        adminStoreItemToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.admin_store_item_toolbar);
        setSupportActionBar(adminStoreItemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAdminStoreItemRecView = (androidx.recyclerview.widget.RecyclerView)findViewById(R.id.admin_store_item_rec_view);
        mAdminStoreItemRecView.setLayoutManager(new GridLayoutManager(AdminStoreItem.this,2));
        FirebaseRecyclerOptions<ModelStoreItemData> options =
                new FirebaseRecyclerOptions.Builder<ModelStoreItemData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Store Item"),ModelStoreItemData.class)
                        .build();




        adapter = new StoreFireBaseAdapter(options,AdminStoreItem.this);
       mAdminStoreItemRecView.setAdapter(adapter);
       adapter.notifyDataSetChanged();






    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_plus_add_store_item,menu);
        MenuItem item = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });


        return true;
    }

   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.add_item:

                Intent intent = new Intent(AdminStoreItem.this,AdminAddStoreItemData.class);
                startActivity(intent);

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
    private void processSearch(String s){
        s = StoreFragment.toTitleCase(s);
        FirebaseRecyclerOptions<ModelStoreItemData> options = new
                FirebaseRecyclerOptions.Builder<ModelStoreItemData>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Store Item").orderByChild("name").startAt(s).endAt(s+"uf8ff"),ModelStoreItemData.class)
                .build();

        adapter = new StoreFireBaseAdapter(options,MainActivity.context);
        adapter.startListening();
        mAdminStoreItemRecView.setAdapter(adapter);


    }
}