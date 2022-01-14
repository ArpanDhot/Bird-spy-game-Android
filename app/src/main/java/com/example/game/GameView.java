package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//This class is going to handle every update and printing activity
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private Bird bird;
    private Point birdPoint;
    private Clouds cloudOne;
    private Clouds cloudTwo;
    private Land land;



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

        birdPoint = new Point(300,300);
        bird = new Bird(new Rect(0, 0, 50, 50), birdPoint,getContext());

        //Instance of Cloud
        cloudOne = new Clouds(getContext(),0);
        cloudTwo = new Clouds(getContext(),1);

        //Instance of Land
        land = new Land(getContext());

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

                //Method in the Player class to the drone
                bird.movement(event);

                break;
        }

        //By making it true it will detect all the touches
        return true;



    }


    public void update(){

        bird.update();
    }


    /**
     * @param canvas
     */
    @Override
    public  void  draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        bird.draw(canvas);

        cloudOne.draw(canvas);
        cloudTwo.draw(canvas);

        land.draw(canvas);

    }

}
