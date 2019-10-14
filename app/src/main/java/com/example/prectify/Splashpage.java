package com.example.prectify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splashpage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3200;
    int counter;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splashpage );
        final ProgressBar prg =findViewById( R.id.progressBar );
        /*final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
             //   prg.setProgress(counter);

                if(counter == 100)
                    t.cancel();
            }
        };

        t.schedule(tt,0,22);*/
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                Intent i;
                i = new Intent( Splashpage.this ,SelectType.class );
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

}
