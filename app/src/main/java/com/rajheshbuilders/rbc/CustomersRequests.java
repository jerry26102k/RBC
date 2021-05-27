package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class CustomersRequests extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    androidx.recyclerview.widget.RecyclerView recyclerView;
    CustomerRequestFirebaseAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_requests);


        toolbar = findViewById(R.id.customers_requests_toolbar);
        recyclerView = findViewById(R.id.customers_requests_recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomersRequests.this,LinearLayoutManager.VERTICAL,false));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseRecyclerOptions<ModelDataForCustomersRequest> options =
                new FirebaseRecyclerOptions.Builder<ModelDataForCustomersRequest>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer Requests"),ModelDataForCustomersRequest.class)
                        .build();
        adapter = new CustomerRequestFirebaseAdapter(options,CustomersRequests.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search_store_item);
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
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter.notifyDataSetChanged();

    }
    private void processSearch(String s){
        s = StoreFragment.toTitleCase(s);
        FirebaseRecyclerOptions<ModelDataForCustomersRequest> options = new
                FirebaseRecyclerOptions.Builder<ModelDataForCustomersRequest>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Customer Requests").orderByChild("heading").startAt(s).endAt(s+"uf8ff"),ModelDataForCustomersRequest.class)
                .build();

        adapter = new CustomerRequestFirebaseAdapter(options,CustomersRequests.this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
}