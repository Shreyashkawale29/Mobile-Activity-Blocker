package com.example.mobileactivityblocker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //This Line hides the Action Bar

        setContentView(R.layout.activity_signup);

        Button backtologin=findViewById(R.id.backtologin);
        Button signup=findViewById(R.id.btnsignup);
        EditText edtname=findViewById(R.id.edtName);
        EditText edtemail=findViewById(R.id.signup_edtEmail);
        EditText edtpass=findViewById(R.id.signup_edtPass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
                }

                if (edtemail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your EMail",Toast.LENGTH_SHORT).show();
                }

                if (edtpass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }

                FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

                if (!edtname.getText().toString().isEmpty() && edtemail.getText().toString().isEmpty() && edtpass.getText().toString().isEmpty());

                firebaseAuth.createUserWithEmailAndPassword(edtemail.getText().toString(),edtpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Intent signupsuccess=new Intent(SignupActivity.this,DashBoard.class);
                            startActivity(signupsuccess);
                        }

                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }


                    }
                });



            }
        });





        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtologin=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(backtologin);
            }
        });





    }
}