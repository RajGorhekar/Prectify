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
import com.github.chrisbanes.photoview.PhotoView;
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
        userimage=(ImageView)findViewById( R.id.ivImage2 );
       /* change=(Button)findViewById(R.id.button9);
        status=(TextView)findViewById(R.id.sta);*/
//        FirebaseUser user3= FirebaseAuth.getInstance().getCurrentUser();

        final Bundle mBundle=getIntent().getExtras();

        if(mBundle!=null);
            userdescription.setText( mBundle.getString( "Description" ) );

            //userimage.setImageResource( mBundle.getInt( "Image" ) );
        Glide.with(this)
                .load(mBundle.getString("Image"))
                .into(userimage);
      /*  if(user3 != null){
           change.setVisibility(View.GONE);

        }
        else{
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
                    pictureDialog.setTitle("STATUS");
                    String [] pictureDialogItems={"SEEN","SOLVED"};
                    pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 1 :

                                    break;
                                case 2 :

                                    break;
                            }
                }
            });
                    pictureDialog.show();
        }*/

//        myAdapter= new MyAdapter( DetailActivity.this,myUserList );
//        mRecyclerView.setAdapter(myAdapter);
//        databaseReference = FirebaseDatabase.getInstance().getReference("Description");
//        databaseReference1 = FirebaseDatabase.getInstance().getReference("Description").child("status");

       /* eventListener =databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myUserList.clear();
                for(DataSnapshot itemsnapshot:dataSnapshot.getChildren()){
                    UserData userData=itemsnapshot.getValue(UserData.class);
                        if(userData.getqDescription().equals(userdescription.getText().toString())){
                            itemsnapshot.getRef().child("status").setValue("See")


.
                        }
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                photoView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(userimage)));
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });
    }
}
