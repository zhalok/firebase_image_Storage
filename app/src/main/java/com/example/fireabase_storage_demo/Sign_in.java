package com.example.fireabase_storage_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {
   private EditText email,password;
   private Button signin,signup;
   private FirebaseAuth firebaseAuth;
   private ProgressBar progressBar;
   private int progressStatus=0;
   private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        signin=(Button)findViewById(R.id.signin);
        signup=(Button)findViewById(R.id.signup);
        handler = new Handler();
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        firebaseAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signin:
               runProgressbar();
               progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Signed in successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Sign_in.this,Uploading.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"User not registered",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

            case R.id.signup:

                Intent intent = new Intent(Sign_in.this,SIgnup.class);
                startActivity(intent);


                break;

        }
    }

    public void runProgressbar()
    {
        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 10000) {
                    progressStatus += 4;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        // Sleep for 300 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}