package com.example.fireabase_storage_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.SplittableRandom;

public class Show_images extends AppCompatActivity {


    DatabaseReference databaseReference;
    ArrayList<String>arrayList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList= new ArrayList<String>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String temp_uri=dataSnapshot.getValue(String.class);
                    String uri= new String();
                    int end_position=0;
                    for(int i=0;i<temp_uri.length();i++)
                    {
                      if(temp_uri.charAt(i)=='?')
                      {
                          end_position=i;
                          break;
                      }

                    }

                    uri=temp_uri.substring(0,end_position);


                    arrayList.add(uri);
                }
                if(arrayList.size()==0) {
                    Toast.makeText(getApplicationContext(),"There are no images to show",Toast.LENGTH_LONG).show();
                    finish();

                }
                Toast.makeText(getApplicationContext(),"Here are ur images",Toast.LENGTH_LONG).show();


                ShowAlertDialogue();

                Adapter adapter = new Adapter(Show_images.this,arrayList);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getApplicationContext(),"There was a problem loading the images",Toast.LENGTH_LONG).show();
            }
        });
    }

    void ShowAlertDialogue()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<arrayList.size();i++)
            stringBuilder.append(arrayList.get(i)+"\n");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image URLs");
        builder.setMessage(stringBuilder.toString());
        builder.create();
        builder.show();


    }






}