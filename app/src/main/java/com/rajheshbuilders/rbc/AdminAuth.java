package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminAuth extends AppCompatActivity {
    EditText mId;
    EditText mPassword;
    Button mLogin;
    DatabaseReference reference;
    boolean bool = false;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_auth);
        mId = (EditText)findViewById(R.id.admin_id);
        mPassword = (EditText)findViewById(R.id.admin_password);
        mLogin = (Button)findViewById(R.id.admin_login_btn);
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.adb_toolbar_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOk();

            }
        });


    }
    public  void isOk() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String myID = snap.child("Id").getValue().toString().trim();
                        String myInputId = mId.getText().toString().trim();
                        String myPassword = snap.child("Password").getValue().toString().trim();
                        String myInputPassword = mPassword.getText().toString().trim();


                        if (myID.equals(myInputId) && myPassword.equals(myInputPassword)) {
                            Intent intent = new Intent(AdminAuth.this, AdminDashboard.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(AdminAuth.this, "Wrong id or password", Toast.LENGTH_SHORT).show();


                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    }




