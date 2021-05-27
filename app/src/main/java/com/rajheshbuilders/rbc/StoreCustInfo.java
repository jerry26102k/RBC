package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StoreCustInfo extends AppCompatActivity {

    EditText mName,mPhoneNO,mEmail,mAddress,mDesc;
    Button mStoreBtn;
    TextView mProductType;
    androidx.appcompat.widget.Toolbar toolbar;
    String date;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_cust_info);
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.store_toolbar);
         setSupportActionBar(toolbar);
         ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mName = (EditText)findViewById(R.id.name_store);
        mPhoneNO = (EditText)findViewById(R.id.phone_number_store);
        mEmail = (EditText)findViewById(R.id.email_address_store);
        mAddress = (EditText)findViewById(R.id.address_store);
        mDesc = (EditText)findViewById(R.id.quantity_of_material);
        mStoreBtn = (Button)findViewById(R.id.store_submit_button);
        mProductType = (TextView)findViewById(R.id.store_product);
       mProductType.setText("You want to a purchase " + getIntent().getStringExtra("ProductName"));

        Calendar calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());



       mStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOk()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("heading","Material Supply "+getIntent().getStringExtra("ProductName"));
                    map.put("name",mName.getText().toString().trim());
                    map.put("phoneNo",mPhoneNO.getText().toString().trim());
                    map.put("email",mEmail.getText().toString().trim());
                    map.put("address",mAddress.getText().toString().trim());
                    map.put("description",mDesc.getText().toString().trim());
                    map.put("date",date);
                    ProgressDialog dialog = new ProgressDialog(StoreCustInfo.this);
                    dialog.setTitle("Uploading your data, Please wait");
                    dialog.show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Customer Requests");
                    reference.push().setValue(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(StoreCustInfo.this,Final.class);
                                    intent.putExtra("final text","we have got your request for material supply! we will contact you as soon as possible");
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(StoreCustInfo.this, "please Check Your network connection and try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
    private boolean isOk(){
        if(!TextUtils.isEmpty(mName.getText().toString().trim()) && !TextUtils.isEmpty(mPhoneNO.getText().toString().trim())
                && !TextUtils.isEmpty(mEmail.getText().toString().trim()) && !TextUtils.isEmpty(mAddress.getText().toString().trim())
                && !TextUtils.isEmpty(mDesc.getText().toString().trim())  ){
            return true;
        }
        else {

            Toast.makeText(this, "Please fill all the details completely", Toast.LENGTH_SHORT).show();
            return false ;
        }

    }

}