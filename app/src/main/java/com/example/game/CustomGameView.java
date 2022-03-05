package com.example.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;

public class CustomGameView extends SurfaceView implements SurfaceHolder.Callback {

    private MediaPlayer backGroundMusic;
    private MediaPlayer birdHitSound;
    private MediaPlayer birdEatSound;
    private MediaPlayer birdItemSound;
    private SharedPreferences sharedPreferences;
    private boolean musicOnOff;
    private int musicVolume;

    private CustomGameViewThread thread;
    private Random random;

    private Bird bird;
    private Point birdPoint;

    private ArrayList<PlaneBullet> aiDrones = new ArrayList<>();
    private Point aiDronePoint;

    private ArrayList<Block> blocks = new ArrayList<>();
    private Point blockPoint;


    private Bitmap canvasBackground;


    public CustomGameView(Context context){
        super(context);

        //Access to the underlying surface is provided via the SurfaceHolder interface,
        // which can be retrieved by calling getHolder()
        getHolder().addCallback(this);

        //Instantiating MainThread class that we made
        thread = new CustomGameViewThread(getHolder(),this);

        canvasBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundcustomlevel);

        //Instance of the music data
        sharedPreferences = context.getSharedPreferences("gameSettings", 0);
        //If the user has not initialised the data base. Therefore it will be returning nothing in that case we will require to have a default value.
        // In this instance we set it to true, therefore the music is going to be On by default.
        musicOnOff = sharedPreferences.getBoolean("musicOnOff", true);
        // In this instance we set it to 50, therefore the music volume is going to have a volume of 50.
        musicVolume = sharedPreferences.getInt("musicVolume", 50);

        //Setting up the background music
        backGroundMusic = MediaPlayer.create(context, R.raw.main_background_music); //assigning the track to the MediaPlayer
        int setVolume = musicVolume; //Choosing volume amount
        final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
        backGroundMusic.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
        if (musicOnOff) {
            backGroundMusic.start();
        }

        //Setting up the bird hit sound
        birdHitSound = MediaPlayer.create(context, R.raw.hit); //assigning the track to the MediaPlayer



        random = new Random();


        //All the main objects
        birdPoint = new Point(300, 300);
        bird = new Bird(new Rect(0, 0, 50, 50), birdPoint, getContext());


        //Loading the block data
        loadBlockData();


        setFocusable(true);

    }


    private void blockBirdCollision(){


        for (Block block : blocks) {
            if(Rect.intersects(bird.getRectangle(), block.getRectangle())){
                bird.setHealth(5);
            }
        }


    }


    private void loadBlockData() {

        //Calling the custom class to load the Json array
        JSONDatabaseManager jsonDatabaseManager = new JSONDatabaseManager(this.getContext());
        JSONArray jsonArray = jsonDatabaseManager.jsonReadData("jsonDataBase");

        //Adding the blocks on the arena
        for (int i = 0; i < jsonArray.length(); i++) {
            blockPoint = new Point(0, 0);
            blocks.add(new Block(new Rect(0, 0, 50, 50), blockPoint, getContext(), 0));
            try {
                blocks.get(i).setJson(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void aiDrone() {

        if(aiDrones.size()<2){ //checking if there are lees then two bullets than spawn a new bullets
            aiDronePoint = new Point(random.nextInt(2000), random.nextInt(1000)); //Getting the new coordinates and assigning to the ship bullet
            aiDrones.add(new PlaneBullet(new Rect(0, 0, 30, 30), aiDronePoint, getContext(), bird,2)); //Adding a new object of ship bullet
            aiDrones.get(aiDrones.size() - 1).setyVel(random.nextInt(10));
        }

        //To declare the bullet dead if it intersects and reduce the bird health. Sound effect on collision
        for (PlaneBullet aiDrone : aiDrones) {
            if (Rect.intersects(bird.getRectangle(), aiDrone.getRectangle())) { //Checking if any element of the array list collides
                aiDrone.setDead(true); //Setting the bullet is dead
                bird.setHealth(bird.getHealth() - aiDrone.getBirdDamage()); //Setting new health to the bird
                bird.setBirdExplosion(true); //Triggering the bird explosion

                //Setting up bird hitting sound
                int setVolume = musicVolume; //Choosing volume amount
                final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
                birdHitSound.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
                if (musicOnOff) {
                    birdHitSound.start();
                }

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


    public void update() {
        bird.update();

        //Updating all the bad drones
        for (PlaneBullet aiDrone : aiDrones) {
            aiDrone.update();
        }

        //Updating the blocks
        for (Block block : blocks) {
            block.update();
        }

        //Calling method to handle block and bird collision
        blockBirdCollision();

        //Calling the Drone Method
        aiDrone();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(Bitmap.createScaledBitmap(canvasBackground, 2000, 800, true), 0, 0, null);


        //Drawing the bird
        bird.draw(canvas);

        //Drawing all the bad drones
        for (PlaneBullet aiDrone : aiDrones) {
            aiDrone.draw(canvas);
        }

        //Drawing the blocks
        for (Block block : blocks) {
            block.draw(canvas);
        }

    }

}
