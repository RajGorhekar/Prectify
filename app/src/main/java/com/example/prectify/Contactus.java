package com.example.prectify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Contactus extends AppCompatActivity {
 ImageView mail;
 TextView raj,sak,jai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        mail =findViewById(R.id.imageView4);
        raj =findViewById(R.id.raj);
        jai =findViewById(R.id.jai);
        sak =findViewById(R.id.sak);
        raj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8446417445"));
                startActivity(intent);
            }
        });
        sak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8446417445"));
                startActivity(intent);
            }
        });
        jai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8446417445"));
                startActivity(intent);
            }
        });


       /* call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER,"8446417445");
                startActivity(intent);
            }
        });*/

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,"querify69@gmail.com");
                intent.setType("message/rfc822");
                startActivity(intent);
            }
        });
    }
}
