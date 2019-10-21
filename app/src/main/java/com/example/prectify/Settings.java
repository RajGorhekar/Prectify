package com.example.prectify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.droidsonroids.gif.GifImageView;

public class Settings extends AppCompatActivity {

    Button cme,cmp,change1,change2;
    EditText aa,bb;
    int i = 10;
    GifImageView p;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        p=findViewById(R.id.p);
        p.setVisibility(View.GONE);
        cme = findViewById(R.id.cem);
        cmp = findViewById(R.id.cpwd);
        change1 = findViewById(R.id.change1);
        change1.setVisibility(View.GONE);
        aa=findViewById(R.id.edtxt1);
        aa.setVisibility(View.GONE);
        change2 = findViewById(R.id.change2);
        change2.setVisibility(View.GONE);
        bb=findViewById(R.id.edtxt2);
        bb.setVisibility(View.GONE);


        cme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aa.getVisibility()== View.VISIBLE ){
                    aa.setVisibility(View.GONE);
                    change1.setVisibility(View.GONE);
                    aa.setText("");
                }
                else if (aa.getVisibility()== View.GONE){
                    aa.setText("");
                    aa.setVisibility(View.VISIBLE);
                    change1.setVisibility(View.VISIBLE);
                }
            }
        });


        cmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bb.getVisibility()== View.VISIBLE ){
                    bb.setVisibility(View.GONE);
                    change2.setVisibility(View.GONE);
                    aa.setText("");
                }
                else if (bb.getVisibility()== View.GONE){
                    bb.setVisibility(View.VISIBLE);
                    change2.setVisibility(View.VISIBLE);
                }
            }
        });

        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change2.setVisibility(View.GONE);
                p.setVisibility(View.VISIBLE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = aa.getText().toString();
                if (TextUtils.isEmpty(bb.getText().toString())) {
                    p.setVisibility(View.GONE);
                    bb.setError("Please enter Password");
                    return;
                }
                else if(cmp.getText().toString().length() < 6) {
                    p.setVisibility(View.GONE);
                    bb.setError("Minimum length of password should be 6");
                    return;
                }
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    p.setVisibility(View.GONE);
                                    aa.setVisibility(View.GONE);
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
                                    reference1.child("password").setValue(aa.getText().toString());
                                    Toast.makeText(Settings.this, "Password changed succesfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        change1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setVisibility(View.VISIBLE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (TextUtils.isEmpty(aa.getText().toString())) {
                    p.setVisibility(View.GONE);
                    aa.setError("Please enter email ID");
                    return;
                }

                user.updateEmail(aa.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    p.setVisibility(View.GONE);
                                    aa.setVisibility(View.GONE);
                                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
                                    databaseReference.child("email").setValue(aa.getText().toString());
                                    Toast.makeText(Settings.this, "Email changed succesfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        }




    }

