package com.example.prectify;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RadioButton;

        import androidx.appcompat.app.AppCompatActivity;

public class QueryType extends AppCompatActivity {
    Button academics;
    Button technical;
    Button hygiene;
    Button food;
    Button others;
    String i;
    String querytype;

    @Override
    public void onBackPressed(){
        Intent intent;
        intent = new Intent( QueryType.this , MainActivity.class );
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.qt);
        academics = findViewById( R.id.radioButton );
        technical = findViewById( R.id.radioButton2 );
        hygiene = findViewById( R.id.radioButton3 );
        food = findViewById( R.id.radioButton4 );
        others = findViewById( R.id.radioButton6 );

        academics.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                i="1";
                querytype="Academics";
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                intent.putExtra("query_type",querytype);
                startActivity(intent);
                finish();

            }
        } );
        technical.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                i="2";
                querytype="Technical";
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                intent.putExtra("query_type",querytype);
                startActivity(intent);
                finish();

            }
        } );
        hygiene.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                i="3";
                querytype="Hygiene";
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                intent.putExtra("query_type",querytype);
                startActivity(intent);
                finish();

            }
        } );
        food.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                i="4";
                querytype="Food";
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                intent.putExtra("query_type",querytype);
                startActivity(intent);
                finish();

            }
        } );
        others.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                i="5";
                querytype="Others";
                Intent intent;
                intent = new Intent( QueryType.this , RaiseQuery.class );
                intent.putExtra("query_type",querytype);
                startActivity(intent);
                finish();

            }
        } );

    }
}
