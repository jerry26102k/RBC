package com.rajheshbuilders.rbc;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConstructionFragment extends Fragment {
    androidx.recyclerview.widget.RecyclerView mConsRecView;
    MyFirebaseRecyclerAdapter adapter;
    androidx.cardview.widget.CardView makeConstructionRequestCard,whatWeProvide;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_construction,container,false);
        mConsRecView = (androidx.recyclerview.widget.RecyclerView)view.findViewById(R.id.construction_rec_view);
        mConsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        makeConstructionRequestCard = (androidx.cardview.widget.CardView)view.findViewById(R.id.make_construction_request);
       // whatWeProvide = (androidx.cardview.widget.CardView)view.findViewById(R.id.what_we_provide_cardview);
        makeConstructionRequestCard.setBackgroundResource(R.drawable.service_card_view_background5);
       // whatWeProvide.setBackgroundResource(R.drawable.service_card_view_background5);
        FirebaseRecyclerOptions<ModelDataForCurrentCons> options =
                new FirebaseRecyclerOptions.Builder<ModelDataForCurrentCons>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Current Construction Work"),ModelDataForCurrentCons.class)
                        .build();


        adapter = new MyFirebaseRecyclerAdapter(options,container.getContext(),2);
        mConsRecView.setAdapter(adapter);


        makeConstructionRequestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context,ConstructionCustomerInfo.class);
                MainActivity.context.startActivity(intent);
            }
        });


        return view;
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
}
