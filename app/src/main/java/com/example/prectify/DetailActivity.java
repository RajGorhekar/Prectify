package com.example.prectify;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    TextView userdescription;
    ImageView userimage;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        userdescription =(TextView)findViewById( R.id.txtdescription );
        userimage=(ImageView)findViewById( R.id.ivImage2 );

        Bundle mBundle=getIntent().getExtras();

        if(mBundle!=null);
            userdescription.setText( mBundle.getString( "Description" ) );

            //userimage.setImageResource( mBundle.getInt( "Image" ) );
        Glide.with(this)
                .load(mBundle.getString("Image"))
                .into(userimage)
        ;
    }
}
