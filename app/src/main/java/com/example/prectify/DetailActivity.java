package com.example.prectify;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView userdescription;
    ImageView userimage;

    Button change;
    TextView status;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,databaseReference1;
    private ValueEventListener eventListener;

    FirebaseUser firebaseUser;
    MyAdapter myAdapter;
    RecyclerView mRecyclerView;
    List<UserData> myUserList;




    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        userdescription =(TextView)findViewById( R.id.txtdescription );
        userimage=findViewById( R.id.ivImage2 );
       /* change=(Button)findViewById(R.id.button9);
        status=(TextView)findViewById(R.id.sta);*/
//        FirebaseUser user3= FirebaseAuth.getInstance().getCurrentUser();

        final Bundle mBundle=getIntent().getExtras();

        if(mBundle!=null);
            userdescription.setText( mBundle.getString( "Description" ) );

        Glide.with(this)
                .load(mBundle.getString("Image"))
                .into(userimage);

    }
}
