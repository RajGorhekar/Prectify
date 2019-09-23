package com.example.prectify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectType extends AppCompatActivity {
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_type );
        Button btnStudent = findViewById( R.id.button2 );
        btnStudent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( SelectType.this , StuLogin.class );
                startActivity(intent);
                finish();
            }
        } );

    }
}
