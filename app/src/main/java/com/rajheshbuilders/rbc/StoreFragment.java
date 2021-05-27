package com.rajheshbuilders.rbc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreFragment extends Fragment{
    androidx.recyclerview.widget.RecyclerView storeRecView;
    StoreFireBaseAdapter adapter;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        storeRecView = (androidx.recyclerview.widget.RecyclerView)view.findViewById(R.id.store_rec_view);
        storeRecView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        FirebaseRecyclerOptions<ModelStoreItemData> options = new
                FirebaseRecyclerOptions.Builder<ModelStoreItemData>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Store Item"),ModelStoreItemData.class)
                .build();
        adapter = new StoreFireBaseAdapter(options,MainActivity.context);
        storeRecView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    private void processSearch(String s){
        s = toTitleCase(s);

        FirebaseRecyclerOptions<ModelStoreItemData> options = new
                FirebaseRecyclerOptions.Builder<ModelStoreItemData>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Store Item").orderByChild("name").startAt(s).endAt(s+"uf8ff"),ModelStoreItemData.class)
                .build();

        adapter = new StoreFireBaseAdapter(options,MainActivity.context);
        adapter.startListening();
        storeRecView.setAdapter(adapter);


    }

    public static String toTitleCase(String string) {

        if (string == null) {

            return null;
        }

        boolean whiteSpace = true;

        StringBuilder builder = new StringBuilder(string); // String builder to store string
        final int builderLength = builder.length();

        for (int i = 0; i < builderLength; ++i) {

            char c = builder.charAt(i); // Get character at builders position

            if (whiteSpace) {

                // Check if character is not white space
                if (!Character.isWhitespace(c)) {

                    // Convert to title case and leave whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    whiteSpace = false;
                }
            } else if (Character.isWhitespace(c)) {

                whiteSpace = true; // Set character is white space

            } else {

                builder.setCharAt(i, Character.toLowerCase(c)); // Set character to lowercase
            }
        }

        return builder.toString(); // Return builders text
    }

}
