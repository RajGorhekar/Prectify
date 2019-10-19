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

    Button cme,cmp,change,delete,yes,no;
    EditText aa;
    TextView sure;
    int i = 10;
    GifImageView p;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference ;
    SharedPreferences st;
    SharedPreferences sr;
    SharedPreferences spr;
    SharedPreferences fl;
    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        p=findViewById(R.id.p);
        p.setVisibility(View.GONE);
        cme = findViewById(R.id.cem);
        cmp = findViewById(R.id.cpwd);
        change = findViewById(R.id.change);
        change.setVisibility(View.GONE);
        delete= findViewById(R.id.dma);
        yes = findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        no = findViewById(R.id.no);
        no.setVisibility(View.GONE);
        aa=findViewById(R.id.edtxt);
        aa.setVisibility(View.GONE);
        sure=findViewById(R.id.textView19);
        sure.setVisibility(View.GONE);


        cme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.setText("");

                if(aa.getVisibility()== View.VISIBLE && i==1){
                    aa.setVisibility(View.VISIBLE);
                    aa.setText("");
                    Toast.makeText(Settings.this, " Enter new email", Toast.LENGTH_SHORT).show();
                }
                i=0;
                if(aa.getVisibility()== View.VISIBLE && i==0){
                    aa.setVisibility(View.GONE);
                    change.setVisibility(View.GONE);
                    aa.setText("");
                }
                else if (aa.getVisibility()== View.GONE){
                    aa.setVisibility(View.VISIBLE);
                    change.setVisibility(View.VISIBLE);
                }
            }
        });


        cmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.setText("");
                if(aa.getVisibility()== View.VISIBLE && i==0){
                    aa.setVisibility(View.VISIBLE);
                    aa.setText("");
                    Toast.makeText(Settings.this, " Enter new Password", Toast.LENGTH_SHORT).show();
                }
                i=1;
                if(aa.getVisibility()== View.VISIBLE && i==1){
                    aa.setVisibility(View.GONE);
                    change.setVisibility(View.GONE);
                    aa.setText("");
                }
                else if (aa.getVisibility()== View.GONE){
                    aa.setVisibility(View.VISIBLE);
                    change.setVisibility(View.VISIBLE);
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change.setVisibility(View.GONE);
                p.setVisibility(View.VISIBLE);
                if (i == 1) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String newPassword = aa.getText().toString();
                    if(TextUtils.isEmpty( cmp.getText().toString() )){
                        p.setVisibility(View.GONE);
                        aa.setError( "Please enter Password" );
                        return;
                    }
                    if (cmp.getText().toString().length() < 6) {
                        p.setVisibility(View.GONE);
                        aa.setError("Minimum length of password should be 6");
                        return;
                    }
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        p.setVisibility(View.GONE);
                                        aa.setVisibility(View.GONE);
                                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
                                        reference1.child("password").setValue(aa.getText().toString());
                                        Toast.makeText(Settings.this, "Password changed succesfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                if(i==0){
                    p.setVisibility(View.VISIBLE);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(TextUtils.isEmpty( cme.getText().toString())){
                        p.setVisibility(View.GONE);
                        aa.setError( "Please enter email ID" );
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
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setVisibility(View.VISIBLE);
                no.setVisibility(View.VISIBLE);
                sure.setVisibility(View.VISIBLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                p.setVisibility(View.VISIBLE);

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent;
                                    intent = new Intent(Settings.this,SelectType.class);
                                    startActivity(intent);
                                    finish();
                                    p.setVisibility(View.GONE);
                                   /* MainActivity m =new MainActivity();
                                    m.sp.edit().putBoolean("logged",false).apply();
                                    m.st.edit().putBoolean("stlogged",false).apply();
                                    m.sr.edit().putBoolean("srlogged",false).apply();
                                    m.spr.edit().putBoolean("registered",false).apply();
                                    m.fl.edit().putBoolean("Faclogged",false).apply();
                                    m.sf.edit().putBoolean("sflogged",false).apply();*/
                                    /*st.edit().putBoolean("stlogged",false).apply();
                                    sr.edit().putBoolean("srlogged",false).apply();
                                    spr.edit().putBoolean("registered",false).apply();
                                    fl.edit().putBoolean("Faclogged",false).apply();
                                    sf.edit().putBoolean("sflogged",false).apply();
                                    Toast.makeText(Settings.this, "Account Deleted succesfully", Toast.LENGTH_SHORT).show();*/
                                }
                            }
                        });


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setVisibility(View.GONE);
                no.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
            }
        });

    }
}
