package com.example.prectify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splashpage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3200;
    int counter;
    int i;
    String s = "Snap Share Solve  ";
    TextView tv;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splashpage );
        final ProgressBar prg =findViewById( R.id.progressBar );
        tv =findViewById(R.id.abc);
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

       /* new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                Intent i;
                i = new Intent( Splashpage.this ,SelectType.class );
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);*/
       /*for(i=0 ;i <= 15; i++){
            new Handler(  ).postDelayed( new Runnable(){
                @Override
                public void run(){
                    tv.setText(s.substring(0,i+1));
                }
            },195*(i+1));
        }*/
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap");
            }
        },1000);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Share");
            }
        },2000);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Share Solve");
            }
        },3000);
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
