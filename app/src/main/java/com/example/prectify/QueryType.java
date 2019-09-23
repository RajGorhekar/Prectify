package com.example.prectify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class QueryType extends AppCompatActivity {
    RadioButton academics;
    RadioButton technical;
    RadioButton hygiene;
    RadioButton food;
    RadioButton others;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_query_type );
        academics = findViewById( R.id.radioButton );
        technical = findViewById( R.id.radioButton2 );
        hygiene = findViewById( R.id.radioButton3 );
        food = findViewById( R.id.radioButton4 );
        others = findViewById( R.id.radioButton6 );
        academics.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                startActivity(intent);
            }
        } );
        technical.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                startActivity(intent);
            }
        } );
        hygiene.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                startActivity(intent);
            }
        } );
        food.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                startActivity(intent);
            }
        } );
        others.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                startActivity(intent);
            }
        } );

    }
}
