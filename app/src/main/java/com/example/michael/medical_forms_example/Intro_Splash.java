package com.example.michael.copaydirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Intro_Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_copay_splash_view);

        View title = getWindow().findViewById(android.R.id.title);
        View titleBar = (View) title.getParent();
        titleBar.setBackgroundColor(getResources().getColor(R.color.blue));

        /* New Handler to start the App_Home.java
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the App_Home.java */
                Intent mainIntent = new Intent(Intro_Splash.this,App_Home.class);
                Intro_Splash.this.startActivity(mainIntent);
                Intro_Splash.this.finish();

                                }  //ends run method
        }, SPLASH_DISPLAY_LENGTH);
    }  //ends onCreate method
                                       }  //ends Activity