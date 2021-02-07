package com.example.fireabase_storage_demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Uploading extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button choose_button,upload_button,display_button;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);
        imageView=(ImageView)findViewById(R.id.myimage);
        choose_button=(Button)findViewById(R.id.choose_button);
        upload_button=(Button)findViewById(R.id.upload_button);
        display_button=(Button)findViewById(R.id.upload_button);
        storageReference= FirebaseStorage.getInstance().getReference("Pic Information");
        databaseReference= FirebaseDatabase.getInstance().getReference("Information");
        choose_button.setOnClickListener(this);
        upload_button.setOnClickListener(this);
        display_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.choose_button:


                getData();

                break;
            case R.id.upload_button:

                saveData();

                break;

        }
    }

    private void getData() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);


    }

    private String getExtension(Uri imageUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void saveData()
    {
        StorageReference ref=storageReference.child(System.currentTimeMillis()+"."+getExtension(imageUri));
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Toast.makeText(getApplicationContext(),"Image Stored Successfully",Toast.LENGTH_SHORT).show();
                        String info=taskSnapshot.getUploadSessionUri().toString();
                        String key=databaseReference.push().getKey();
                        databaseReference.child(key).setValue(info);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"There was a problem storing the data",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);

        }
    }

}