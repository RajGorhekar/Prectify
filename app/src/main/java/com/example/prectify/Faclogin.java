package com.example.prectify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Faclogin extends AppCompatActivity{
    EditText pass;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faclogin);
        pass = findViewById(R.id.editText6);
        login = findViewById(R.id. button7);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass.getText().toString().equals("spit@123")){
                    Intent intent;
                    intent = new Intent( Faclogin.this , MainActivity.class );
                    startActivity(intent);
                    Toast.makeText( Faclogin.this , "You are logged in as a Faculty" , Toast.LENGTH_LONG).show();

                    finish();
                }else if (!pass.getText().toString().equals("spit@123")) {
                    pass.setError("Wrong Password");
                }
            }
        });



    }
}
