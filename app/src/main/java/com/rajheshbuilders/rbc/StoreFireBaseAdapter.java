package com.rajheshbuilders.rbc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class StoreFireBaseAdapter extends FirebaseRecyclerAdapter<ModelStoreItemData,StoreFireBaseAdapter.StoreViewHolder>
{
    Context context;

    public StoreFireBaseAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ModelStoreItemData> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull StoreFireBaseAdapter.StoreViewHolder holder, final int position, @NonNull @NotNull ModelStoreItemData model) {
       holder.itemName.setText(model.getName());
        holder.itemPrice.setText(model.getPrice());
        Picasso.get().load(Uri.parse(model.getImageUri().trim())).into(holder.itemImage);
        holder.itemCardview.setBackgroundResource(R.drawable.store_item_card_view_background);
        if(context == MainActivity.context){
            holder.itemMenuOption.setVisibility(View.GONE);

        }


       holder.itemMenuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(),v);
                menu.inflate(R.menu.edit_delete_admin);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.edit_admin_activity:{
                                final DialogPlus dialogPlus2 = DialogPlus.newDialog(context)
                                        .setContentHolder(new ViewHolder(R.layout.edit_store_item_dialog))
                                        .setExpanded(true,690)
                                        .create();
                                View mView = dialogPlus2.getHolderView();
                                EditText mImageUrl = mView.findViewById(R.id.edit_store_item_image_url);
                                EditText mName = mView.findViewById(R.id.edit_store_item_name);
                                EditText mPrice = mView.findViewById(R.id.edit_store_item_price);

                                Button mUpdate = mView.findViewById(R.id.store_item_update_button);

                                mImageUrl.setText(model.getImageUri());
                                mName.setText(model.getName());
                                mPrice.setText(model.getPrice());

                                dialogPlus2.show();

                                mUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ModelStoreItemData modelStoreItemData =
                                                new ModelStoreItemData(mName.getText().toString().trim(),
                                                        mPrice.getText().toString().trim(),
                                                        mImageUrl.getText().toString().trim());
                                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                .getReference().child("Store Item").child(getRef(position).getKey());
                                        reference.setValue(modelStoreItemData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialogPlus2.dismiss();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                dialogPlus2.dismiss();

                                            }
                                        });

                                    }
                                });




                                return true;}
                            case R.id.delete_admin_activity:{

                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemMenuOption.getContext());
                                builder.setTitle("delete panel");
                                builder.setMessage("are you sure? Data will be deleted permanently..");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                            FirebaseDatabase.getInstance().getReference().child("Store Item").child(getRef(position).getKey()).removeValue();
                                        Toast.makeText(context, "Store item for  "+model.getName()+" is deleted successfully", Toast.LENGTH_SHORT).show();
                                        ((Activity)context).finish();


                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder.show();

                                return true;}
                        }



                        return true;
                    }
                });
                menu.show();
            }
        });
       if(context == MainActivity.context){
           holder.itemCardview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context,StoreCustInfo.class);
                   intent.putExtra("ProductName",model.getName());
                   context.startActivity(intent);
               }
           });
       }


    }

    @NonNull
    @NotNull
    @Override
    public StoreFireBaseAdapter.StoreViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        return new StoreViewHolder(view);
    }

    class StoreViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemPrice,itemName;
        ImageView itemMenuOption;
        androidx.cardview.widget.CardView itemCardview;

        public StoreViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.store_item_image);
            itemName = itemView.findViewById(R.id.store_item_name);
            itemPrice = itemView.findViewById(R.id.store_item_price);
            itemCardview = itemView.findViewById(R.id.store_item_cardView);
            itemMenuOption = itemView.findViewById(R.id.store_menu_options);
        }
    }
}
