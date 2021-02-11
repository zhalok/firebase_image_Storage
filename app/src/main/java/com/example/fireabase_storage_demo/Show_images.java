package com.example.fireabase_storage_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_images extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<String>arrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        arrayList= new ArrayList<String>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String uri=dataSnapshot.getValue(String.class);
                    arrayList.add(uri);
                }
                if(arrayList.size()==0) {
                    Toast.makeText(getApplicationContext(),"There are no images to show",Toast.LENGTH_LONG).show();
                    finish();

                }
                Toast.makeText(getApplicationContext(),"Here are ur images",Toast.LENGTH_LONG).show();

                StringBuilder stringBuilder = new StringBuilder();

               // recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getApplicationContext(),"There was a problem loading the images",Toast.LENGTH_LONG).show();
            }
        });
    }



}