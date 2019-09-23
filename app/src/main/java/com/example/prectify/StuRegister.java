package com.example.prectify;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class StuRegister extends AppCompatActivity {
    Button btnRegister;
    EditText etemailId;
    EditText etpwd ;
    ProgressBar pgb;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        setContentView( R.layout.activity_stu_register );
        btnRegister=(Button)findViewById( R.id.button );
        etemailId=(EditText) findViewById( R.id.editText11 );
        etpwd=(EditText)findViewById( R.id.editText12);
        pgb = (ProgressBar)findViewById( R.id.progressBar2 );


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
            if (!(email.isEmpty() && password.isEmpty())){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( StuRegister.this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete ( @NonNull Task<AuthResult> task ) {

                        pgb.setVisibility( View.VISIBLE );
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(StuRegister.this, MainActivity.class));
                        }
                        else {
                            pgb.setVisibility( View.INVISIBLE );
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
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
}
