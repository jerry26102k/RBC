package com.rajheshbuilders.rbc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageSlider homePageImageSlider;
    RecyclerView mRec;
    myArrayAdapter adapter;
    ArrayAdapterForServices serAdapter;
    RecyclerView mSerRec;
    CardView cardView;
    TextView mAdmin;



    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        homePageImageSlider = view.findViewById(R.id.image_slider);



        List<SlideModel> mySliderList = new ArrayList<>();
        mySliderList.add(new SlideModel(R.drawable.index, ScaleTypes.FIT));
        mySliderList.add(new SlideModel(R.drawable.three,ScaleTypes.FIT));
        mySliderList.add(new SlideModel(R.drawable.index, ScaleTypes.FIT));
        mySliderList.add(new SlideModel(R.drawable.three,ScaleTypes.FIT));

        homePageImageSlider.setImageList(mySliderList, ScaleTypes.FIT);







    }



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {



          View view = inflater.inflate(R.layout.fragment_home,container,false);
       mSerRec = view.findViewById(R.id.ser_recyclerView);
        mSerRec.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));
        serAdapter = new ArrayAdapterForServices(getServices(),getContext());
        mSerRec.setAdapter(serAdapter);
          mRec = view.findViewById(R.id.home_page_recycler_view);
          mRec.setLayoutManager(new LinearLayoutManager(view.getContext()));

           mAdmin = (view.findViewById(R.id.i_am_admin));


     /*   FirebaseRecyclerOptions<ModelDataForCurrentCons> options = new FirebaseRecyclerOptions.Builder<ModelDataForCurrentCons>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Best Construction Work"),ModelDataForCurrentCons.class)
                .build();

        adapter = new MyFirebaseRecyclerAdapter(options,MainActivity.context,1);
        mRec.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

           mAdmin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(view.getContext(),AdminAuth.class);
                   startActivity(intent);
               }
           });

           adapter = new myArrayAdapter(getData());
           mRec.setAdapter(adapter);



          return view;


    }

  /*  @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }*/

    public ArrayList<ModelData> getData(){
        ArrayList<ModelData> data = new ArrayList<>();
        data.add(new ModelData("Haunted Villa","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.index));
        data.add(new ModelData("Avengers Headquaters","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.three));
        data.add(new ModelData("Dr Strange House","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.index));
        data.add(new ModelData("Bridge","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.three));
        data.add(new ModelData("White House","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.index));
        data.add(new ModelData("Resident House","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.three));
        data.add(new ModelData("School","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.index));
        data.add(new ModelData("Shop","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.three));
        data.add(new ModelData("Villa","This haunted villa was made in 2018" +
                " by team of 70 workers.It was bit scary project.I don\'t know how I made this" +
                " project possible.","Hogwarts","2 Acres",R.drawable.index));
        return data;
    }
   public ArrayList<DataModelForServices> getServices(){
        ArrayList<DataModelForServices> servicesData = new ArrayList<>();
        servicesData.add(new DataModelForServices("Construction",R.drawable.service_card_view_background1,R.drawable.construction_card));
        servicesData.add(new DataModelForServices("Interior Design",R.drawable.service_card_view_background2,R.drawable.interior_card));
        servicesData.add(new DataModelForServices("Material Supply",R.drawable.service_card_view_background3,R.drawable.materialsupply_card));
        servicesData.add(new DataModelForServices("Site Engineer",R.drawable.service_card_view_background4,R.drawable.site_engineer));
        servicesData.add(new DataModelForServices("Consultant",R.drawable.service_card_view_background5,R.drawable.consultant));
       servicesData.add(new DataModelForServices("Map Designing",R.drawable.service_card_view_background1,R.drawable.consultant));

        return servicesData;
    }

}
