package com.rajheshbuilders.rbc;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class myArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ModelData> mInfo;

    public myArrayAdapter(ArrayList<ModelData> mInfo) {
        this.mInfo = mInfo;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.construction_info_cardview,parent,false);

        return new ViewHolderForConsWork(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderForConsWork viewHolderForConsWork = (ViewHolderForConsWork)holder;
        viewHolderForConsWork.mLocationAndArea.setText(mInfo.get(position).getmLocation()+" | "+mInfo.get(position).getmArea());
        viewHolderForConsWork.mDesc.setText(mInfo.get(position).getmDesc());

        viewHolderForConsWork.mHeading.setText(mInfo.get(position).getmHeader());
        viewHolderForConsWork.mImg.setImageResource(mInfo.get(position).getmImg());

    }

    @Override
    public int getItemCount() {
        return mInfo.size();
    }

    class ViewHolderForConsWork extends RecyclerView.ViewHolder{
        ImageView mImg;
        TextView mHeading,mDesc,mLocationAndArea;


        public ViewHolderForConsWork(@NonNull @NotNull View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.cons_info_img);
            mHeading = (TextView)itemView.findViewById((R.id.cons_info_heading));
            mDesc = (TextView)itemView.findViewById((R.id.cons_info_desc));

            mLocationAndArea =(TextView)itemView.findViewById(R.id.cons_info_location_and_area);
        }
    }

}
