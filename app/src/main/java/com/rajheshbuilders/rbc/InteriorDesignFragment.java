package com.rajheshbuilders.rbc;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class InteriorDesignFragment extends Fragment {
    androidx.recyclerview.widget.RecyclerView mIntRecView;
    androidx.cardview.widget.CardView makeInteriorDesignRequestCard;
    MyFirebaseRecyclerAdapter adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interior_design,container,false);
        mIntRecView = (androidx.recyclerview.widget.RecyclerView)view.findViewById(R.id.interiorDesign__rec_view);
        mIntRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        makeInteriorDesignRequestCard = (androidx.cardview.widget.CardView)view.findViewById(R.id.make_interiorDesign_request);
        makeInteriorDesignRequestCard.setBackgroundResource(R.drawable.service_card_view_background5);
        FirebaseRecyclerOptions<ModelDataForCurrentCons> options =
                new FirebaseRecyclerOptions.Builder<ModelDataForCurrentCons>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Interior Design"),ModelDataForCurrentCons.class)
                        .build();


        adapter = new MyFirebaseRecyclerAdapter(options,container.getContext(),0,"Interior Design");
        mIntRecView.setAdapter(adapter);


        makeInteriorDesignRequestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context,InteriorsCustomerInfo.class);
                intent.putExtra("key","Interior Design");
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
