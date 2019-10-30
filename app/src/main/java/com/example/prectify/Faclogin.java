package com.example.prectify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Faclogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText pass;
    Button login;
    SharedPreferences fl;
    SharedPreferences sf;
    Spinner spin;
    String querytypename;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        fl.edit().putBoolean("Faclogged",false).apply();
        Intent intent;
        intent = new Intent( Faclogin.this , SelectType.class );
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faclogin);
        pass = findViewById(R.id.editText6);
        login = findViewById(R.id. button7);
        spin=findViewById(R.id.spinner);
        fl=getSharedPreferences("Faclogin",MODE_PRIVATE);
        sf=getSharedPreferences("sflogin",MODE_PRIVATE);
        fl.edit().putBoolean("Faclogged",false).apply();
        sf.edit().putBoolean("sflogged",false).apply();
        /*if(sf.getBoolean("sflogged",true) && fl.getBoolean("Faclogged",true)){
            goToMainActivity();
        }*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pass.getText().toString().equals("spit@123")) {
                    pass.setError("Wrong Password");
                }if(pass.getText().toString().equals("spit@123")){
                    fl.edit().putBoolean("Faclogged",true).apply();
                    sf.edit().putBoolean("sflogged",true).apply();
                    Intent intent;
                    intent = new Intent( Faclogin.this , MainActivity.class );
                    intent.putExtra("type",querytypename);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }
    public void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       querytypename = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, querytypename, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
