package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button newGameButton;
    private Button loadGameButton;
    private Button settingsButton;
    private Button helpButton;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is hiding the hide the OS bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //To make the windows full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Setting up the activity
        setContentView(R.layout.activity_menu);

        //Setting up the buttons by assigning their IDs
        newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(e -> {
            Intent intentTwo = new Intent(MenuActivity.this,GameLoading.class);
             startActivity(intentTwo);

        });

        loadGameButton = findViewById(R.id.loadGameButton);
        loadGameButton.setOnClickListener(e -> {

        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(e -> {

        });

        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(e -> {

        });
    }
}