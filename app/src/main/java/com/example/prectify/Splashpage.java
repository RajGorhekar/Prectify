package com.example.prectify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class Splashpage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3200;
    int counter;
    int i=0;
    GifImageView prg;
    String s = "Snap Share Solve  ";
    TextView tv;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splashpage );
        prg =findViewById( R.id.progressBar );
        tv =findViewById(R.id.abc);
    int time = 3000/20;
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("S");
            }
        },time);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Sn");
            }
        },time*2);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Sna");
            }
        },time*3);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap");
            }
        },time*4);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap ");
            }
        },time*5);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap S");
            }
        },time*6);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Sh");
            }
        },time*7);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Sha");
            }
        },time*8);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Shar");
            }
        },time*9);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Share");
            }
        },time*10);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Share ");
            }
        },time*11);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){
                tv.setText("Snap Share S");
            }
        },time*12);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){ tv.setText("Snap Share So");
            }
        },time*13);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){ tv.setText("Snap Share Sol");
            }
        },time*14);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){ tv.setText("Snap Share Solv");
            }
        },time*15);
        new Handler(  ).postDelayed( new Runnable(){
            @Override
            public void run(){ tv.setText("Snap Share Solve");
            }
        },time*16);



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
