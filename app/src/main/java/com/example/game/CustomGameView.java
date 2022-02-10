package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class CustomGameView extends SurfaceView implements SurfaceHolder.Callback {

    private CustomGameViewThread thread;
    private Random random;

    private Bird bird;
    private Point birdPoint;

    private ArrayList<PlaneBullet> aiDrones = new ArrayList<>();
    private Point aiDronePoint;

    private Bitmap canvasBackground;


    public CustomGameView(Context context){
        super(context);

        //Access to the underlying surface is provided via the SurfaceHolder interface,
        // which can be retrieved by calling getHolder()
        getHolder().addCallback(this);

        //Instantiating MainThread class that we made
        thread = new CustomGameViewThread(getHolder(),this);

        canvasBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.mainbackground);

        random = new Random();


        //All the main objects
        birdPoint = new Point(300, 300);
        bird = new Bird(new Rect(0, 0, 50, 50), birdPoint, getContext());




        setFocusable(true);

    }

    private void aiDrone() {

        if(aiDrones.size()<2){ //checking if there are lees then two bullets than spawn a new bullets
            aiDronePoint = new Point(random.nextInt(2000), random.nextInt(1000)); //Getting the new coordinates and assigning to the ship bullet
            aiDrones.add(new PlaneBullet(new Rect(0, 0, 30, 30), aiDronePoint, getContext(), bird)); //Adding a new object of ship bullet
            aiDrones.get(aiDrones.size() - 1).setyVel(random.nextInt(10));
        }

        //To declare the bullet dead if it intersects and reduce the bird health. Sound effect on collision
        for (PlaneBullet aiDrone : aiDrones) {
            if (Rect.intersects(bird.getRectangle(), aiDrone.getRectangle())) { //Checking if any element of the array list collides
                aiDrone.setDead(true); //Setting the bullet is dead
                bird.setHealth(bird.getHealth() - aiDrone.getBirdDamage()); //Setting new health to the bird
                bird.setBirdExplosion(true); //Triggering the bird explosion

                //Setting up bird hitting sound

            }
        }

        aiDrones.removeIf(aiDrones -> (aiDrones.isDead())); //Checking if the bullet is dead if so we gonna remove it
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
        thread = new CustomGameViewThread(getHolder(),this);


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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //In order to move any object this is the method that will detect any touch on the surfaceView
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                //Calling the bird movement method
                bird.movement(event);

                break;
        }

        //By making it true it will detect all the touches
        return true;
    }


    public void update(){
        bird.update();

        //Updating all the drones
        for (PlaneBullet aiDrone : aiDrones) {
            aiDrone.update();
        }
        //Calling the Drone Method
        aiDrone();

    }

    @Override
    public  void  draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(canvasBackground, 0, 0, null);

        bird.draw(canvas);

        for (PlaneBullet aiDrone : aiDrones) {
            aiDrone.draw(canvas);
        }

    }

}
