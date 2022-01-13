package com.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


//If you face issue of


/**
 * In this class I will load all of the resources and the surface view.
 */
public class GameLoading extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Getting Created");
        //To make the windows full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //To remove the activity bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Setting up the view using the GamePanel class that we build
        setContentView(new GameView(this));

    }
}
