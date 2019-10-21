package com.example.prectify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectType extends AppCompatActivity {
    SharedPreferences st;
    SharedPreferences sf;
    Button btnStudent,btnfaculty;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if(!isConnected(SelectType.this)){
            Intent intent;
            intent = new Intent(SelectType.this, Offline.class);
            startActivity(intent);
            finish();
        }
        else{
            setContentView( R.layout.activity_select_type );
            btnStudent = findViewById( R.id.button2 );
            btnfaculty = findViewById(R.id.button1);
            st=getSharedPreferences("stlogin",MODE_PRIVATE);
            sf=getSharedPreferences("sflogin",MODE_PRIVATE);

            if(sf.getBoolean("sflogged",true)){
                goToFaclogin();
            }

            if(st.getBoolean("stlogged",true)){
                goToMainActivity();
                finish();
            }

            btnStudent.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    // st.edit().putBoolean("stlogged",true).apply();
                    Intent intent;
                    intent = new Intent( SelectType.this , StuLogin.class );
                    startActivity(intent);
                    finish();
                }
            } );

            btnfaculty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sf.edit().putBoolean("sflogged",true).apply();
                    Intent intent;
                    intent = new Intent( SelectType.this , Faclogin.class );
                    startActivity(intent);
                    finish();
                }
            });


        }

    }
    public void goToFaclogin(){
        Intent i=new Intent(this,Faclogin.class);
        startActivity(i);
        finish();
    }
    public void goToMainActivity() {
        Intent i = new Intent(this, StuLogin.class);
        startActivity(i);
        finish();
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

}
