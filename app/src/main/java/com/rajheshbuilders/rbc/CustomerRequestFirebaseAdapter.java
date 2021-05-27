package com.rajheshbuilders.rbc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class CustomerRequestFirebaseAdapter extends FirebaseRecyclerAdapter<ModelDataForCustomersRequest,CustomerRequestFirebaseAdapter.CustomerRequestViewHolder> {

    Context context;

    public CustomerRequestFirebaseAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ModelDataForCustomersRequest> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull CustomerRequestViewHolder holder, final int position, @NonNull @NotNull ModelDataForCustomersRequest model) {
        holder.name.setText(model.getName());
        holder.phoneNo.setText(model.getPhoneNo());
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());
        holder.description.setText(model.getDescription());
        holder.heading.setText(model.getHeading());
        holder.date.setText(model.getDate());
        holder.cardView.setBackgroundResource(R.drawable.store_item_card_view_background);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("delete panel");
                builder.setMessage("are you sure? Data will be deleted permanently..");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Customer Requests").child(getRef(position).getKey()).removeValue();
                        Toast.makeText(context, "request of "+model.getName()+" is deleted successfully", Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();



            }
        });


    }

    @NonNull
    @NotNull
    @Override
    public CustomerRequestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_request_design,parent,false);
        return new CustomerRequestViewHolder(view);
    }

    class CustomerRequestViewHolder extends RecyclerView.ViewHolder{
        TextView heading,name,phoneNo,email,address,description,date;
        ImageView delete;
        androidx.cardview.widget.CardView cardView;

        public CustomerRequestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading_design);
            name = itemView.findViewById(R.id.name_design);
            phoneNo = itemView.findViewById(R.id.phoneNo_design);
            email = itemView.findViewById(R.id.email_design);
            address = itemView.findViewById(R.id.address_design);
            description = itemView.findViewById(R.id.description_design);
            date = itemView.findViewById(R.id.date_design);
            delete = itemView.findViewById(R.id.delete_design);
            cardView = itemView.findViewById(R.id.customer_requests_design_card);

        }
    }
}
