package com.example.prectify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectType extends AppCompatActivity {
    SharedPreferences st;
    SharedPreferences sf;
    Button btnStudent,btnfaculty;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_type );
        btnStudent = findViewById( R.id.button2 );
        btnfaculty = findViewById(R.id.button1);
        st=getSharedPreferences("stlogin",MODE_PRIVATE);
        sf=getSharedPreferences("sflogin",MODE_PRIVATE);

        if(sf.getBoolean("sflogged",true)){
            goToFaclogin();
        }

        if(st.getBoolean("stlogged",true)){
            goToMainActivity();
        }

        btnStudent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
               // st.edit().putBoolean("stlogged",true).apply();
                Intent intent;
                intent = new Intent( SelectType.this , StuLogin.class );
                startActivity(intent);
                finish();
            }
        } );

        btnfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sf.edit().putBoolean("sflogged",true).apply();
                Intent intent;
                intent = new Intent( SelectType.this , Faclogin.class );
                startActivity(intent);
                finish();
            }
        });


    }
    public void goToFaclogin(){
        Intent i=new Intent(this,Faclogin.class);
        startActivity(i);
        finish();
    }
    public void goToMainActivity() {
        Intent i = new Intent(this, StuLogin.class);
        startActivity(i);
        finish();
    }
}
