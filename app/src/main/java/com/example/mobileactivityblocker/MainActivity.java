package com.example.mobileactivityblocker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import android.os.Bundle;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText login_edtEmail;
    EditText login_edtPass;
    Button btnlogin;
    database g;


    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //This Line hides the Action Bar

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);


 //       DataBase implementation
        database g=new database(this);
//        SQLiteDatabase db=
        g.getReadableDatabase();



        TextView gotosignup = findViewById(R.id.signup);
        btnlogin = findViewById(R.id.btnlogin);

        login_edtEmail = findViewById(R.id.Login_edtEmail);
        login_edtPass = findViewById(R.id.Login_edtPass);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=login_edtEmail.getText().toString();
                String password=login_edtPass.getText().toString();

                if (email.equals("") || password.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Enter all the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean i=g.insert_data(email,password);
                    if (i==true)
                    {
                        Toast.makeText(MainActivity.this,"Sucessful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Not Sucessful",Toast.LENGTH_SHORT).show();
                    }
                }

//                addItemToSheet();

                if (login_edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your EMail",Toast.LENGTH_SHORT).show();
                }

                if (login_edtPass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                }

                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

                if (!(login_edtEmail.getText().toString().isEmpty() && login_edtEmail.getText().toString().isEmpty() && login_edtPass.getText().toString().isEmpty())){

                    firebaseAuth.signInWithEmailAndPassword(login_edtEmail.getText().toString(),login_edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent loginsuccess=new Intent(MainActivity.this,DashBoard.class);
                                startActivity(loginsuccess);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(signup);

            }
        });
        firebaseDatabase.getReference().child("Node1").child("Node2").setValue("Nodevalue");

        checkOverlayPermission();


//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addItemToSheet();
//            }
//        });

    }


//    private void addItemToSheet() {
//
//        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this,"Adding Item","Please Wait...");
//        final String email = login_edtEmail.getText().toString().trim();
//        final String pass = login_edtPass.getText().toString().trim();

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwJ7OLx77RI87xBNXag3xq9fTWOzt-PrHW6Qk3-4B4PgF4sfQ9V9nogO_RMA_ej7Clj_w/exec", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, ""+response,Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
//            }
//        }
//        ){
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams(){
//
//               Map<String, String> parmas = new HashMap<>();
//                parmas.put("action","addItem");
//                parmas.put("login_edtEmail","Email");
//                parmas.put("login_edtPass","Password");
//
//
//                return parmas;
//            }
//        };

//        int socketTimeOut = 50000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//        stringRequest.setRetryPolicy(policy);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(stringRequest);
//    }





















    // method to ask user to grant the Overlay permission

    public void checkOverlayPermission(){



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(this)) {

                // send user to the device settings

                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);

                startActivity(myIntent);

            }

        }

    }

}