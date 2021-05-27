package com.rajheshbuilders.rbc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.rajheshbuilders.rbc.DataModelForServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ArrayAdapterForServices extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DataModelForServices> mSerInfo;
    Context context;

    public ArrayAdapterForServices(ArrayList<DataModelForServices> mSerInfo,Context context) {
        this.mSerInfo = mSerInfo;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_veiw_services_item,parent,false);
        return new ViewHolderForServices(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderForServices viewHolderForServices = (ViewHolderForServices)holder;
        viewHolderForServices.mSerImgImg.setImageResource(mSerInfo.get(position).getmSerImg());
        viewHolderForServices.mSerHeaderText.setText(mSerInfo.get(position).getmSerHeader());
        viewHolderForServices.cardView.setBackgroundResource(mSerInfo.get(position).getmColorId());

        viewHolderForServices.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(viewHolderForServices.mSerHeaderText.getText().toString().trim()){
                    case "Site Engineer":
                        intent = new Intent(context,CustInfoSiteEngineneer.class);
                        context.startActivity(intent);
                        break;
                    case "Consultant":
                         intent = new Intent(context,Cunsultancy.class);
                        context.startActivity(intent);
                        break;
                    case "Map Designing":
                        intent = new Intent(context,MapDesigning.class);
                        context.startActivity(intent);
                        break;
                    case "Construction":
                        MainActivity.fragment = new ConstructionFragment();
                        ((Activity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,MainActivity.fragment).commit();
                        break;
                    case "Material Supply":
                        MainActivity.fragment = new StoreFragment();
                        ((Activity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,MainActivity.fragment).commit();
                        break;
                    case "Interior Design":
                        MainActivity.fragment = new InteriorDesignFragment();
                        ((Activity)context).getFragmentManager().beginTransaction().replace(R.id.fragment_container,MainActivity.fragment).commit();
                        break;



                }
            }
        });

       
    }

    @Override
    public int getItemCount() {
        return mSerInfo.size();
    }

    public class ViewHolderForServices extends RecyclerView.ViewHolder {
        TextView mSerHeaderText;
        ImageView mSerImgImg;
        CardView cardView;
        public ViewHolderForServices(@NonNull @NotNull View itemView) {
            super(itemView);
            mSerHeaderText = itemView.findViewById(R.id.text_card_services_item);
            mSerImgImg = itemView.findViewById(R.id.image_card_services_item);
            cardView = itemView.findViewById(R.id.card_services_item);
        }
    }
}
