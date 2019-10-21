package com.example.prectify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.ProgressBar;
import android.widget.TextView;
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
   // ProgressBar prb;
    SharedPreferences sr;
    SharedPreferences spr;
    SharedPreferences sp;
    SharedPreferences st;
    Button login;
    ImageView dis;
    TextView togg;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        st.edit().putBoolean("stlogged",false).apply();
        Intent intent;
        intent = new Intent( StuLogin.this , SelectType.class );
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stu_login );
        Button btnlogin=findViewById( R.id.button5 );
        Button btnreg =findViewById( R.id.button4 );
        mAuth = FirebaseAuth.getInstance();
        etemailId=(EditText) findViewById( R.id.editText2 );
        etpwd=(EditText)findViewById( R.id.editText3);
       // prb=(ProgressBar)findViewById( R.id.progressBar3 );
       // prb.setVisibility( View.GONE );
        //togg= findViewById(R.id.textView19);
        //togg.setVisibility(View.GONE);
        sp=getSharedPreferences("login",MODE_PRIVATE);
        spr=getSharedPreferences("register",MODE_PRIVATE);
        sr=getSharedPreferences("srlogin",MODE_PRIVATE);
        st=getSharedPreferences("stlogin",MODE_PRIVATE);
        login = findViewById(R.id.button4);
        if(spr.getBoolean("registered",true) && sr.getBoolean("srlogged",true) ){
            goToMainActivity();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent( StuLogin.this , StuRegister.class );
                //intent.addFlags(ContactsContract.Intents.FLAG_ACTIVITY_NO_ANIMATION);
                //startActivityForResult(intent,0);
                overridePendingTransition(0,0);
                finish();

            }
        });

        btnreg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( StuLogin.this , StuRegister.class );
               // spr.edit().putBoolean("registered",true).apply();
                //st.edit().putBoolean("stlogged",true).apply();
                overridePendingTransition(0,0);
                startActivity(intent);
                finish();
            }
        } );

        if(sp.getBoolean("logged",true)){
            goToMainActivity();
        }

        /*@Override
        public void onBackPressed(){
            Intent intent;
            intent = new Intent( StuLogin.this , SelectType.class );
            startActivity(intent);
            finish();


        }*/



        btnlogin.setOnClickListener( new View.OnClickListener() {
            String email= etemailId.getText().toString().trim();
            String password= etpwd.getText().toString().trim();
            @Override
            public void onClick ( View view ) {
                final String email = etemailId.getText().toString();
                String password = etpwd.getText().toString();
                if(TextUtils.isEmpty( email )){
                    etemailId.setError( "Please enter email ID" );
                    etemailId.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty( password )){
                    etpwd.setError( "Please enter Password" );
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
                    if (!(TextUtils.isEmpty( email ) && TextUtils.isEmpty( password ))) {
                    //prb.setVisibility( View.VISIBLE );
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( StuLogin.this , new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete ( @NonNull Task<AuthResult> task ) {
                            if (task.isSuccessful()) {
                               // prb.setVisibility( View.INVISIBLE );
                                sp.edit().putBoolean("logged",true).apply();
                                st.edit().putBoolean("stlogged",true).apply();
                                Intent intent = new Intent( StuLogin.this,MainActivity.class );
                                /*intent.putExtra("user_name",etemailId.getText().toString().trim());*/
                                Bundle bundle = new Bundle();
                                bundle.putString("email",email);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                                Toast.makeText( StuLogin.this,"Login successful",Toast.LENGTH_SHORT ).show();
                            } else {
                               // prb.setVisibility( View.INVISIBLE );
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
        finish();

    }



}