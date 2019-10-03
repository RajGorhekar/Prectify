package com.example.prectify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class StuRegister extends AppCompatActivity {
    Button btnRegister;
    EditText etemailId;
    EditText etpwd ;
    ProgressBar pgb;
    private FirebaseAuth mAuth;
    SharedPreferences sr;
    FirebaseUser firebaseUser;
    EditText name;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        setContentView( R.layout.activity_stu_register );
        btnRegister=(Button)findViewById( R.id.button );
        etemailId=(EditText) findViewById( R.id.editText11 );
        etpwd=(EditText)findViewById( R.id.editText12);
        pgb = (ProgressBar)findViewById( R.id.progressBar2 );
        pgb.setVisibility( View.INVISIBLE );
        name=findViewById(R.id.editText);
        sr=getSharedPreferences("srlogin",MODE_PRIVATE);
        if(sr.getBoolean("srlogged",true)){
            goToMainActivity();
        }


        final String name1 = name.getText().toString().trim();


        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                String email= etemailId.getText().toString().trim();
                String password= etpwd.getText().toString().trim();
                if(TextUtils.isEmpty( email )){
                    etemailId.setError("Please enter email ID");
                    etemailId.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty( password)){
                    etpwd.setError("Please enter password");
                    etpwd.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etemailId.setError("Please enter a valid email");
                    etemailId.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    etpwd.setError("Minimum length of password should be 6");
                    etpwd.requestFocus();
                    return;
                }
                if (!TextUtils.isEmpty( email ) && !TextUtils.isEmpty( password )){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( StuRegister.this , new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete ( @NonNull Task<AuthResult> task ) {
                            pgb.setVisibility( View.VISIBLE );
                            if(task.isSuccessful()){
                                pgb.setVisibility( View.INVISIBLE );
                                /*firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name1).build();

                                firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(StuRegister.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );*/

                                startActivity( new Intent(getApplicationContext(),StuLogin.class) );
                                sr.edit().putBoolean("srlogged",true).apply();
                                Toast.makeText( StuRegister.this,"registration successful",Toast.LENGTH_SHORT ).show();
                            }
                            if(!task.isSuccessful()){
                                pgb.setVisibility( View.INVISIBLE );
                                Toast.makeText( StuRegister.this,"registration unsuccessful, please try again",Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );
                }
                else{
                    Toast.makeText( StuRegister.this,"Error occurred",Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }
    public void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }


}