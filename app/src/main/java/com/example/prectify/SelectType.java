package com.example.prectify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectType extends AppCompatActivity {
    SharedPreferences st;
    SharedPreferences sr;
    SharedPreferences sp;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_type );
        Button btnStudent = findViewById( R.id.button2 );
        st=getSharedPreferences("stlogin",MODE_PRIVATE);
        sr=getSharedPreferences("srlogin",MODE_PRIVATE);
        sp=getSharedPreferences("login",MODE_PRIVATE);

        if(st.getBoolean("stlogged",true) && sp.getBoolean("logged",true) || st.getBoolean("stlogged",true) && sr.getBoolean("srlogged",true) ){
            goToMainActivity();
        }

        btnStudent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                st.edit().putBoolean("stlogged",true).apply();
                Intent intent;
                intent = new Intent( SelectType.this , StuLogin.class );
                startActivity(intent);
                finish();
            }
        } );

    }
    public void goToMainActivity() {
        Intent i = new Intent(this, StuLogin.class);
        startActivity(i);
    }
}