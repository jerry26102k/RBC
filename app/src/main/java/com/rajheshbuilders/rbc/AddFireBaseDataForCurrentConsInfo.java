package com.rajheshbuilders.rbc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class AddFireBaseDataForCurrentConsInfo extends AppCompatActivity {
    ImageView mAddCurrentConstImage;
    EditText mAddCurrentConstHeading,mAddCurrentConstDesc,mAddCurrentConstLocation,mAddCurrentConstArea,mAddCurrentConstStatus;
    androidx.appcompat.widget.AppCompatButton mAddCurrentConstAddBtn;
    androidx.appcompat.widget.Toolbar mAddCurrentConstToolbar;
    Uri mFilePathImg;
    Bitmap mBitmapImg;
    DatabaseReference reference;
    StorageReference currentConsImageRef;
    String s;

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
        setContentView(R.layout.activity_add_fire_base_data_for_current_cons_info);

        mAddCurrentConstToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar_add_current_cons_info);
        setSupportActionBar(mAddCurrentConstToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAddCurrentConstAddBtn = (androidx.appcompat.widget.AppCompatButton)findViewById(R.id.add_current_const_add_btn);
        mAddCurrentConstArea = (EditText)findViewById(R.id.add_current_const_area);
        mAddCurrentConstDesc = (EditText)findViewById(R.id.add_current_const_desc);
        mAddCurrentConstHeading = (EditText)findViewById(R.id.add_current_const_heading);
        mAddCurrentConstImage = (ImageView)findViewById(R.id.add_current_const_img);
        mAddCurrentConstLocation = (EditText)findViewById(R.id.add_current_const_location);
        mAddCurrentConstStatus = (EditText)findViewById(R.id.add_current_const_status);

        s = getIntent().getStringExtra("key");
        if(s.equals("Interior Design")){
            mAddCurrentConstStatus.setVisibility(View.GONE);
            mAddCurrentConstArea.setVisibility(View.GONE);
            mAddCurrentConstLocation.setVisibility(View.GONE);


        }

        mAddCurrentConstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AddFireBaseDataForCurrentConsInfo.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"please choose image to save"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }
                        }).check();

            }
        });
        mAddCurrentConstAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFilePathImg!=null) {
                    storeDataToFireBase();
                }else{
                    Toast.makeText(AddFireBaseDataForCurrentConsInfo.this, "Please select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 &&  data != null && resultCode == RESULT_OK){
            mFilePathImg = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(mFilePathImg);
                mBitmapImg = BitmapFactory.decodeStream(inputStream);
                mAddCurrentConstImage.setImageBitmap(mBitmapImg);


            }catch(Exception e){
                Toast.makeText(this, "There was a problem uploading your image", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Can't find the image ", Toast.LENGTH_SHORT).show();
        }

    }
    private void storeDataToFireBase(){
        ProgressDialog dialog = new ProgressDialog(AddFireBaseDataForCurrentConsInfo.this);
        dialog.setTitle("Uploading your data, Please wait");
        dialog.show();
         reference = FirebaseDatabase.getInstance().getReference(s);

        currentConsImageRef = FirebaseStorage.getInstance().getReference().child("Images");
        currentConsImageRef.child(String.valueOf(System.currentTimeMillis())+"."+getImageExtension()).putFile(mFilePathImg)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      dialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AddFireBaseDataForCurrentConsInfo.this, "Upload failed!! Please try again.", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       ModelDataForCurrentCons modelDataForCurrentCons = new ModelDataForCurrentCons(mAddCurrentConstHeading.getText().toString().trim(),
                                mAddCurrentConstDesc.getText().toString().trim(),mAddCurrentConstLocation.getText().toString().trim(),
                                mAddCurrentConstArea.getText().toString().trim(),mAddCurrentConstStatus.getText().toString().trim(),uri.toString().trim());
                       reference.push().setValue(modelDataForCurrentCons);

                        Toast.makeText(AddFireBaseDataForCurrentConsInfo.this, "data uploaded successfully", Toast.LENGTH_SHORT).show();



                        finish();

                    }
                });

            }
        });

    }
    private String getImageExtension(){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mFilePathImg));
    }
}