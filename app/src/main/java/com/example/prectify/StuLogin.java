package com.example.prectify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class StuLogin extends AppCompatActivity {

    EditText etemailId;
    EditText etpwd ;
    private FirebaseAuth mAuth;
    ProgressBar prb;
    SharedPreferences sp;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stu_login );
        Button btnlogin=findViewById( R.id.button5 );
        Button btnreg =findViewById( R.id.button4 );
        mAuth = FirebaseAuth.getInstance();
        etemailId=(EditText) findViewById( R.id.editText2 );
        etpwd=(EditText)findViewById( R.id.editText3);
        prb=(ProgressBar)findViewById( R.id.progressBar3 );
        prb.setVisibility( View.GONE );
        sp=getSharedPreferences("login",MODE_PRIVATE);

        btnreg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( StuLogin.this , StuRegister.class );
                startActivity(intent);
                finish();
            }
        } );
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }

        btnlogin.setOnClickListener( new View.OnClickListener() {
           String email= etemailId.getText().toString().trim();
            String password= etpwd.getText().toString().trim();
            @Override
            public void onClick ( View view ) {
                goToMainActivity();
                sp.edit().putBoolean("logged",true).apply();

                String email = etemailId.getText().toString();
                String password = etpwd.getText().toString();
                if(TextUtils.isEmpty( email )){
                    etemailId.setError( "Please enter email ID" );
                    etemailId.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty( password )){
                    etpwd.setError( "Please enter password" );
                    etpwd.requestFocus();
                    return;
                }
                if (!(TextUtils.isEmpty( email ) && TextUtils.isEmpty( password ))) {
                    prb.setVisibility( View.VISIBLE );
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( StuLogin.this , new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete ( @NonNull Task<AuthResult> task ) {
                            if (task.isSuccessful()) {
                                prb.setVisibility( View.INVISIBLE );
                                startActivity( new Intent( StuLogin.this,MainActivity.class ) );
                                Toast.makeText( StuLogin.this,"Login successful",Toast.LENGTH_SHORT ).show();
                            } else {
                                prb.setVisibility( View.INVISIBLE );
                                Toast.makeText( StuLogin.this , "login error" , Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

                }
            }
        } );}
        public void goToMainActivity() {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }


    
}
