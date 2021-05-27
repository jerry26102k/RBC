package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustInfoSiteEngineneer extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    EditText name,phoneNo,email,address,description;
    Button submit;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_info_site_engineneer);

        name = (EditText)findViewById(R.id.name_site_engineer);
        phoneNo = (EditText)findViewById(R.id.phone_number_site_engineer);
        email = (EditText)findViewById(R.id.email_address_site_engineer);
        address = (EditText)findViewById(R.id.address_site_enginer);
        description = (EditText)findViewById(R.id.description_site_engineer);
        submit = (Button)findViewById(R.id.site_engineer_submit_button);

        toolbar = findViewById(R.id.site_engineer_request_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar calendar = Calendar.getInstance();
        date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOk()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("heading","Site Engineer");
                    map.put("name",name.getText().toString().trim());
                    map.put("phoneNo",phoneNo.getText().toString().trim());
                    map.put("email",email.getText().toString().trim());
                    map.put("address",address.getText().toString().trim());
                    map.put("description",description.getText().toString().trim());
                    map.put("date",date);
                    ProgressDialog dialog = new ProgressDialog(CustInfoSiteEngineneer.this);
                    dialog.setTitle("Sending your request, Please wait");
                    dialog.show();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Customer Requests");
                    reference.push().setValue(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(CustInfoSiteEngineneer.this,Final.class);
                                    intent.putExtra("final text","we have got your request for site engineer! we will contact you as soon as possible");
                                    startActivity(intent);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(CustInfoSiteEngineneer.this, "please check your network connection and try again ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOk(){
        if(!TextUtils.isEmpty(name.getText().toString().trim()) && !TextUtils.isEmpty(phoneNo.getText().toString().trim()) &&
                !TextUtils.isEmpty(email.getText().toString().trim()) && !TextUtils.isEmpty(address.getText().toString().trim()) &&
                !TextUtils.isEmpty(description.getText().toString().trim()) ){
            return  true;
        }
        else{
            Toast.makeText(this, "please fill all details completely", Toast.LENGTH_SHORT).show();
            return  false;
        }
    }
}