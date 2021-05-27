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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
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

public class AdminAddStoreItemData extends AppCompatActivity {


    ImageView mStoreItemImage;
    EditText mStoreItemName,mStoreItemPrice;
    androidx.appcompat.widget.AppCompatButton mStoreItemAddButton;
    androidx.appcompat.widget.Toolbar mStoreItemToolbar;
    Uri mFilePathImg;
    Bitmap mBitmapImg;
    DatabaseReference reference;
    StorageReference storeItemImageRef;

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
        setContentView(R.layout.activity_admin_add_store_item_data);

        mStoreItemToolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar_add_store_item);
        setSupportActionBar(mStoreItemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mStoreItemAddButton = (androidx.appcompat.widget.AppCompatButton)findViewById(R.id.add_store_item_btn);
        mStoreItemName = (EditText)findViewById(R.id.add_store_item_name);
        mStoreItemPrice = (EditText)findViewById(R.id.add_store_item_price);
        mStoreItemImage = (ImageView)findViewById(R.id.add_store_item_img);

        mStoreItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AdminAddStoreItemData.this)
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
        mStoreItemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFilePathImg!=null) {
                    storeDataToFireBase();
                }else{
                    Toast.makeText(AdminAddStoreItemData.this, "Please select Image", Toast.LENGTH_SHORT).show();
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
                mStoreItemImage.setImageBitmap(mBitmapImg);


            }catch(Exception e){
                Toast.makeText(this, "There was a problem uploading your image", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(this, "Can't find the image ", Toast.LENGTH_SHORT).show();
        }

    }
    private void storeDataToFireBase(){
        ProgressDialog dialog = new ProgressDialog(AdminAddStoreItemData.this);
        dialog.setTitle("Uploading your data, Please wait");
        dialog.show();
        reference = FirebaseDatabase.getInstance().getReference("Store Item");

        storeItemImageRef = FirebaseStorage.getInstance().getReference().child("storeItemImages");
        storeItemImageRef.child(String.valueOf(System.currentTimeMillis())+"."+getImageExtension()).putFile(mFilePathImg)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AdminAddStoreItemData.this, "Upload failed!! Please try again.", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ModelStoreItemData modelStoreItemData = new ModelStoreItemData(mStoreItemName.getText().toString().trim(),
                                mStoreItemPrice.getText().toString().trim(),uri.toString().trim());
                        reference.push().setValue(modelStoreItemData);

                        Toast.makeText(AdminAddStoreItemData.this, "data uploaded successfully", Toast.LENGTH_SHORT).show();



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