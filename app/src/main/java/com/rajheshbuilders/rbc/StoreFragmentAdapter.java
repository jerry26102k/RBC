package com.rajheshbuilders.rbc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoreFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static ArrayList<DataModelForServices> mStoreItem;
    public static int mInt = 0;


    public StoreFragmentAdapter(ArrayList<DataModelForServices> mStoreItem, Context context) {
        this.mStoreItem = mStoreItem;
        this.context = context;
    }

    Context context;
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_veiw_services_item,parent,false);


        return new ViewHolderForStoreItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        StoreFragmentAdapter.ViewHolderForStoreItem viewHolderStoreItem = (StoreFragmentAdapter.ViewHolderForStoreItem)holder;
        viewHolderStoreItem.mSerImgImg.setImageResource(mStoreItem.get(position).getmSerImg());
        viewHolderStoreItem.mSerHeaderText.setText(mStoreItem.get(position).getmSerHeader());
        viewHolderStoreItem.cardView.setBackgroundResource(mStoreItem.get(position).getmColorId());

        viewHolderStoreItem.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInt = position;

                Intent intent = new Intent(context,StoreCustInfo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mStoreItem.size();
    }
    public class  ViewHolderForStoreItem extends RecyclerView.ViewHolder{
        TextView mSerHeaderText;
        ImageView mSerImgImg;
        CardView cardView;
        public ViewHolderForStoreItem(@NonNull @NotNull View itemView) {
            super(itemView);
            mSerHeaderText = itemView.findViewById(R.id.text_card_services_item);
            mSerImgImg = itemView.findViewById(R.id.image_card_services_item);
            cardView = itemView.findViewById(R.id.card_services_item);

        }
    }
}
