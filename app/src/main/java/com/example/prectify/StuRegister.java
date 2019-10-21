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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.droidsonroids.gif.GifImageView;

public class StuRegister extends AppCompatActivity {
    Button btnRegister;

    GifImageView pgb;
    private FirebaseAuth mAuth;
    SharedPreferences sr;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    EditText name;
    String name1;
    EditText uid;
    String uid1;
    EditText etemailId;
    String email;
    EditText etpwd ;
    String password;
    String token;
    Button login;
    SharedPreferences spr;
    SharedPreferences st;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        setContentView( R.layout.activity_stu_register );
        btnRegister=(Button)findViewById( R.id.button );
        etemailId=(EditText) findViewById( R.id.editText11 );
        etpwd=(EditText)findViewById( R.id.editText12);
        pgb = findViewById( R.id.progressBar2 );
        uid=findViewById(R.id.editText4);
        pgb.setVisibility( View.INVISIBLE );
        name=findViewById(R.id.editText);
        login = findViewById(R.id.btnLogin);
        spr=getSharedPreferences("register",MODE_PRIVATE);
        st=getSharedPreferences("stlogin",MODE_PRIVATE);
        sr=getSharedPreferences("srlogin",MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(sr.getBoolean("srlogged",true)){
            goToMainActivity();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent( StuRegister.this , StuLogin.class );
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });

        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                email= etemailId.getText().toString().trim();
                password= etpwd.getText().toString().trim();

                name1 = name.getText().toString().trim();
                uid1 = uid.getText().toString().trim();
                pgb.setVisibility( View.VISIBLE );

                if(TextUtils.isEmpty( email )){
                    pgb.setVisibility( View.INVISIBLE );
                    etemailId.setError("Please enter email ID");
                    etemailId.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty( password)){
                    pgb.setVisibility( View.INVISIBLE );
                    etpwd.setError("Please enter password");
                    etpwd.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    pgb.setVisibility( View.INVISIBLE );
                    etemailId.setError("Please enter a valid email");
                    etemailId.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    pgb.setVisibility( View.INVISIBLE );
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
                                pgb.setVisibility( View.VISIBLE );
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                token = FirebaseAuth.getInstance().getUid();
                                writeNewUser(name1 ,email,  uid1 , password , token);

                                startActivity( new Intent(getApplicationContext(),StuLogin.class) );
                                finish();
                                sr.edit().putBoolean("srlogged",true).apply();
                                spr.edit().putBoolean("registered",true).apply();
                                st.edit().putBoolean("stlogged",true).apply();
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
                    pgb.setVisibility( View.INVISIBLE );
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
    private void writeNewUser( String name, String email , String uid , String password , String token) {
        User user = new User(name, email, uid , password);

        databaseReference.child("users").child(token).setValue(user);
    }



}