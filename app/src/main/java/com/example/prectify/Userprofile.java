package com.example.prectify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userprofile extends AppCompatActivity {
    TextView tvname,tvuid,tvemail,tvpass;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String dname,duid,demail,dpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        tvname=findViewById(R.id.textView8);
        tvuid=findViewById(R.id.textView9);
        tvemail=findViewById(R.id.textView10);
        tvpass=findViewById(R.id.textView11);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String token = FirebaseAuth.getInstance().getUid();
       /* User user1= new User();*/
        /*databaseReference = FirebaseDatabase.getInstance().getReference("users").child(token);
        databaseReference = databaseReference.child("username");
        dname =
        duid = databaseReference.child("uid").toString();
        demail  = databaseReference.child("email").toString();
        dpass  = databaseReference.child("password").toString();
*/
        /*tvemail.setText(demail);
        tvname.setText(dname);
        tvuid.setText(duid);
        tvpass.setText(dpass);*/

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dname= (String) dataSnapshot.child("users").child(token).child("username").getValue();
                duid= (String) dataSnapshot.child("uid").getValue();
                demail= (String) dataSnapshot.child("email").getValue();
                dpass= (String) dataSnapshot.child("password").getValue();
                for(DataSnapshot itemsnapshot:dataSnapshot.child("users").getChildren()){
                    User user1 = itemsnapshot.getValue(User.class);
                    if(user1!=null && itemsnapshot.getKey().equals(token)) {
                        dname = user1.username;
                        duid = user1.uid;
                        dpass = user1.password;
                        demail = user1.email;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvemail.setText("Email : "+ demail);
        tvname.setText("Name : " + dname);
        tvuid.setText("Uid : " +  duid);
        tvpass.setText("Name : " +  dpass);






    }
}
