package com.rajheshbuilders.rbc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<ModelDataForCurrentCons,MyFirebaseRecyclerAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    int type;
    String mtype;
    public MyFirebaseRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ModelDataForCurrentCons> options, Context context,int type) {
        super(options);
        this.context = context;
        this.type = type;
    }
    public MyFirebaseRecyclerAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ModelDataForCurrentCons> options, Context context,int type,String s) {
        super(options);
        this.context = context;
        this.type = type;
        this.mtype = s;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, final int position, @NonNull @NotNull ModelDataForCurrentCons model) {

        holder.heading.setText(model.getHeading());
        holder.desc.setText(model.getDescription());
        String s;
        if(type == 1){
            s = model.getLocation()+" | " +model.getArea();

        }else  s = "Status: "+model.getStatus()+"% completed | "+model.getLocation()+" | "
                +model.getArea();
        holder.locationAreaStatus.setText(s);
        Picasso.get().load(Uri.parse(model.getImageUri())).into(holder.img);
        holder.cardView.setBackgroundResource(R.drawable.store_item_card_view_background);
        if(type == 0 && mtype.equals("Interior Design")) holder.locationAreaStatus.setVisibility(View.GONE);

            if(context == MainActivity.context){
            holder.mMenuIcon.setVisibility(View.GONE);

        }


        holder.mMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(),v);
                menu.inflate(R.menu.edit_delete_admin);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.edit_admin_activity:{
                                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                                        .setContentHolder(new ViewHolder(R.layout.edit_dialog))
                                        .setExpanded(true,1000)
                                        .create();
                                View mView = dialogPlus.getHolderView();
                                EditText mImageUrl = mView.findViewById(R.id.edit_current_const_image_url);
                                EditText mHeading = mView.findViewById(R.id.edit_current_const_heading);
                                EditText mDescription = mView.findViewById(R.id.edit_current_const_desc);
                                EditText mLocation = mView.findViewById(R.id.edit_current_const_location);
                                EditText mArea = mView.findViewById(R.id.edit_current_const_area);
                                EditText mStatus = mView.findViewById(R.id.edit_current_const_status);
                                Button mUpdate = mView.findViewById(R.id.edit_current_const_add_btn);
                                if(mtype.equals("Interior Design")){
                                    mArea.setVisibility(View.GONE);
                                    mStatus.setVisibility(View.GONE);
                                    mLocation.setVisibility(View.GONE);

                                }


                                mImageUrl.setText(model.getImageUri());
                                mHeading.setText(model.getHeading());
                                mDescription.setText(model.getDescription());
                                mLocation.setText(model.getLocation());
                                mArea.setText(model.getArea());
                                mStatus.setText(model.getStatus());
                                dialogPlus.show();

                                mUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ModelDataForCurrentCons modelDataForCurrentCons =
                                                new ModelDataForCurrentCons(mHeading.getText().toString().trim(),
                                                        mDescription.getText().toString().trim(),
                                                        mLocation.getText().toString().trim(),
                                                        mArea.getText().toString().trim(),mStatus.getText().toString().trim()
                                                ,mImageUrl.getText().toString().trim());
                                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                .getReference().child(mtype).child(getRef(position).getKey());
                                        reference.setValue(modelDataForCurrentCons)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialogPlus.dismiss();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                dialogPlus.dismiss();

                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                dialogPlus.dismiss();
                                            }
                                        });

                                    }
                                });




                                return true;}
                            case R.id.delete_admin_activity:{
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mMenuIcon.getContext());
                                builder.setTitle("delete panel");
                                builder.setMessage("delete...?");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        FirebaseDatabase.getInstance().getReference().child(mtype).child(getRef(position).getKey()).removeValue();
                                        switch(mtype){
                                            case "Current Construction Work":
                                                Toast.makeText(context, "construction info for  "+model.getHeading()+" is deleted successfully", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "Interior Design":
                                                Toast.makeText(context, "Interior Design info for  "+model.getHeading()+" is deleted successfully", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "Best Construction Work":
                                                Toast.makeText(context, "Home page construction info for  "+model.getHeading()+" is deleted successfully", Toast.LENGTH_SHORT).show();
                                                break;
                                        }

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

    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_cons_info,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView heading,desc,locationAreaStatus;
        ImageView img;
        ImageView mMenuIcon;
        androidx.cardview.widget.CardView cardView;


        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cons_info_img_admin);
            heading = itemView.findViewById(R.id.cons_info_heading_admin);
            desc = itemView.findViewById(R.id.cons_info_desc_admin);
            locationAreaStatus = itemView.findViewById(R.id.cons_info_location_and_area_admin);
            mMenuIcon = itemView.findViewById(R.id.admin_popup_menu);
            cardView = itemView.findViewById(R.id.cons_info_cardView);

        }
    }
}
