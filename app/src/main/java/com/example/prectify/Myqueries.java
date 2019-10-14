package com.example.prectify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Myqueries extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog ;
    FirebaseAuth mauth;
    SwipeRefreshLayout s;
    TextView tve;
    MyAdapter myAdapter;
    RecyclerView mRecyclerView;
    List<UserData> myUserList;
    UserData mUserData;




    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_myqueries);
        tve=(TextView) findViewById( R.id.TextView8 );
        mRecyclerView=findViewById( R.id.recyclerview1);
        mauth = FirebaseAuth.getInstance();
        s= (SwipeRefreshLayout)findViewById(R.id .refresh1);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Myqueries.this,1);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager( llm );
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Loading Items....");
        progressDialog.setCanceledOnTouchOutside(false);
        getSupportActionBar().setTitle("My Queries");
        myUserList = new ArrayList<>(  );

        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override

                    public void run() {

                        s.setRefreshing(false);
                    }

                },3000);
            }
        });
        myAdapter= new MyAdapter( Myqueries.this,myUserList );
        mRecyclerView.setAdapter(myAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Description");
        progressDialog.show();
        eventListener =databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myUserList.clear();
                for(DataSnapshot itemsnapshot:dataSnapshot.getChildren()){
                    UserData userData=itemsnapshot.getValue(UserData.class);
                    if(userData.getToken().equals(mauth.getUid())){
                        myUserList.add(userData);
                    }
                }
                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });
    }
}
   // Intent photopicker = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);