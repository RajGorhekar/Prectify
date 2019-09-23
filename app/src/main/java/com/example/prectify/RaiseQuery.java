package com.example.prectify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RaiseQuery extends AppCompatActivity {
    Button btnsubmit;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_raise_query );
        btnsubmit=findViewById( R.id.button3 );

        btnsubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( RaiseQuery.this , MainActivity.class );
                startActivity(intent);
                finish();
            }
        } );
    }
}
