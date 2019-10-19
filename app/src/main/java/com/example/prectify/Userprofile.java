package com.example.prectify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userprofile extends AppCompatActivity {
    TextView tvname,tvuid,tvemail,tvpass;
    Button view;
    private FirebaseDatabase firebaseDatabase;
    private  FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String dname,duid,demail,dpass;
    String enpass= " ";
    User User1;
    int a = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        tvname=findViewById(R.id.textView8);
        tvuid=findViewById(R.id.textView9);
        tvemail=findViewById(R.id.textView10);
        tvpass=findViewById(R.id.textView11);
        view = findViewById(R.id.button8);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String token = FirebaseAuth.getInstance().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(token);

           databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User1 = dataSnapshot.getValue(User.class);
                tvemail.setText("Email : "+ User1.getemail());
                tvname.setText("Name : " + User1.getusername());
                tvuid.setText("Uid : " +  User1.getuid());

                for(int i =0 ; i<User1.getpassword().length();i++ ){
                    enpass=enpass+"*";
                }
                tvpass.setText("Password  : " +  enpass);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(a==1){
                            tvpass.setText("Password : " +  User1.getpassword());
                            view.setText("Click to hide password");
                            a=0;
                        }
                        else if(a==0){
                            a=1;
                            tvpass.setText("Password  : " +  enpass);
                            view.setText("Click to show password");
                        }


                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Userprofile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
