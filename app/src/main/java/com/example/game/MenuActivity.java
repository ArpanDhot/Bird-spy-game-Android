package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button newGameButton;
    private Button loadGameButton;
    private Button createLevelButton;
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
            Intent newGameIntent = new Intent(MenuActivity.this,GameLoading.class);
            startActivity(newGameIntent);

        });

        loadGameButton = findViewById(R.id.loadGameButton);
        loadGameButton.setOnClickListener(e -> {
            CustomGameView  customGameView = new CustomGameView(getApplicationContext());
            setContentView(customGameView);

        });

        createLevelButton = findViewById(R.id.createLevelButton);
        createLevelButton.setOnClickListener(e->{
            LevelCreate levelCreate = new LevelCreate(getApplicationContext());
            setContentView(levelCreate);
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(e -> {
            Intent settingsIntent = new Intent(MenuActivity.this,Settings.class);
            startActivity(settingsIntent);
        });

        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(e -> {

        });
    }
}