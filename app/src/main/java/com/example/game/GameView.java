package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//This class is going to handle every update and printing activity
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MediaPlayer backGroundMusic;
    private SharedPreferences sharedPreferences;

    private MainThread thread;
    private Bird bird;
    private Point birdPoint;
    private Ship ship;
    private Point shipPoint;
    private Plane plane;
    private Point planePoint;

    private Clouds cloudOne;
    private Clouds cloudTwo;
    private Clouds cloudThree;
    private Land land;

    private boolean musicOnOff;
    private int musicVolume;

    /**
     * @param context
     */
    public GameView(Context context){
        super(context);

        //Access to the underlying surface is provided via the SurfaceHolder interface,
        // which can be retrieved by calling getHolder()
        getHolder().addCallback(this);

        //Instantiating MainThread class that we made
        thread = new MainThread(getHolder(),this);

        /**
         * PUT ANY CODE AFTER THIS
         */

        //Instance of the music data
        sharedPreferences = context.getSharedPreferences("gameSettings",0);
        //If the user has not initialised the data base. Therefore it will be returning nothing in that case we will require to have a default value.
        // In this instance we set it to true, therefore the music is going to be On by default.
        musicOnOff= sharedPreferences.getBoolean("musicOnOff",true);
        // In this instance we set it to 50, therefore the music volume is going to have a volume of 50.
        musicVolume = sharedPreferences.getInt("musicVolume",50);

        //Setting up the background music
        backGroundMusic = MediaPlayer.create(context, R.raw.main_background_music); //assigning the track to the MediaPlayer
        int setVolume =  musicVolume; //Choosing volume amount
        final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
        backGroundMusic.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
        if(musicOnOff){
            backGroundMusic.start();
        }


        //TODO got to change this
        birdPoint = new Point(300,300);
        bird = new Bird(new Rect(0, 0, 50, 50), birdPoint,getContext());

        shipPoint = new Point(200,585);
        ship = new Ship(new Rect(0,0,50,50),shipPoint,getContext());

        planePoint = new Point(200,100);
        plane = new Plane(new Rect(0,0,50,50),planePoint,getContext());

        //Instance of Cloud
        cloudOne = new Clouds(getContext(),0);
        cloudTwo = new Clouds(getContext(),1);
        cloudThree = new Clouds(getContext(),0);

        //Instance of Land
        land = new Land(getContext());

        /**
         * PUT ANY CODE BEFORE THIS
         */

        setFocusable(true);

    }




    //These methods are same to the default activity run cycle that saad explained

    /**
     * This is called immediately after any structural changes (format or size) have been made to the surface
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }


    /**
     * This is called immediately after the surface is first created.
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(),this);


        thread.setRunning(true);
        thread.start();
    }


    /**
     * This is called immediately before a surface is being destroyed.
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean  retry = true;
        while(true){
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e ){
                e.printStackTrace();
            }
            retry=false;
        }

    }



    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        //In order to move any object this is the method that will detect any touch on the surfaceView
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                //This condition checks the bird health and when it becomes less then 0 triggers the game over
                if(bird.getHealth()>0){
                    //Method in the Player class to the drone
                    bird.movement(event);
                }else{
                    Intent intent = new Intent(this.getContext(),GameOver.class);
                    this.getContext().startActivity(intent);
                    ((Activity)this.getContext()).finish();
                }

                break;
        }

        //By making it true it will detect all the touches
        return true;



    }


    public void update(){

        bird.update();
        ship.update();
        plane.update();
    }


    /**
     * @param canvas
     */
    @Override
    public  void  draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        bird.draw(canvas);
        ship.draw(canvas);
        plane.draw(canvas);

        cloudOne.draw(canvas);
        cloudTwo.draw(canvas);
        cloudThree.draw(canvas);

        land.draw(canvas);

    }

}
