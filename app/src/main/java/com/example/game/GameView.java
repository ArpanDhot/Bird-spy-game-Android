package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

//This class is going to handle every update and printing activity
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MediaPlayer backGroundMusic;
    private MediaPlayer birdHitSound;
    private MediaPlayer birdEatSound;
    private MediaPlayer birdItemSound;
    private SharedPreferences sharedPreferences;

    private MainThread thread;

    private Bitmap canvasBackground;

    private Bird bird;
    private Point birdPoint;

    private Ship ship;
    private Point shipPoint;

    private Plane plane;
    private Point planePoint;

    private ScoreItem scoreItem;
    private Point scoreItemPoint;

    private ArrayList<ShipBullet> shipBullets = new ArrayList<ShipBullet>();
    private Point shipBulletPoint;
    private int shipBulletSpawnTimer = 0;

    private ArrayList<PlaneBullet> planeBullets = new ArrayList<>();
    private Point planeBulletPoint;

    private Clouds cloudOne;
    private Clouds cloudTwo;
    private Clouds cloudThree;
    private Land land;

    private boolean musicOnOff;
    private int musicVolume;

    /**
     * @param context
     */
    public GameView(Context context) {
        super(context);

        //Access to the underlying surface is provided via the SurfaceHolder interface,
        // which can be retrieved by calling getHolder()
        getHolder().addCallback(this);

        //Instantiating MainThread class that we made
        thread = new MainThread(getHolder(), this);

        /**
         * PUT ANY CODE AFTER THIS
         */

        canvasBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.mainbackground);

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

        //Setting up the bird hit sound (Rest of the code at line 144)
        birdHitSound = MediaPlayer.create(context, R.raw.hit); //assigning the track to the MediaPlayer

        //Setting up the bird eat sound (Rest of the code at line 170)
        birdItemSound = MediaPlayer.create(context, R.raw.collect_item); //assigning the track to the MediaPlayer

        //Setting up the bird eat sound (Rest of the code at line 186)
        birdEatSound = MediaPlayer.create(context, R.raw.eat); //assigning the track to the MediaPlayer


        //All the main objects
        birdPoint = new Point(300, 300);
        bird = new Bird(new Rect(0, 0, 50, 50), birdPoint, getContext());

        shipPoint = new Point(200, 585);
        ship = new Ship(new Rect(0, 0, 50, 50), shipPoint, getContext());

        planePoint = new Point(200, 100);
        plane = new Plane(new Rect(0, 0, 50, 50), planePoint, getContext());

        scoreItemPoint = new Point(400, 400);
        scoreItem = new ScoreItem(new Rect(0, 0, 30, 30), scoreItemPoint, getContext());

        shipBulletPoint = new Point(ship.getxPos() + 150, ship.getyPos() + 110);
        shipBullets.add(new ShipBullet(new Rect(0, 0, 30, 30), shipBulletPoint, getContext()));

        planeBulletPoint = new Point(plane.getxPos() + 100, plane.getyPos() + 100);
        planeBullets.add(new PlaneBullet(new Rect(0, 0, 30, 30), planeBulletPoint, getContext(), bird));

        //Instance of Cloud
        cloudOne = new Clouds(getContext(), 0);
        cloudTwo = new Clouds(getContext(), 1);
        cloudThree = new Clouds(getContext(), 0);

        //Instance of Land
        land = new Land(getContext());

        /**
         * PUT ANY CODE BEFORE THIS
         */

        setFocusable(true);

    }


    /**
     * MY METHODS
     */

    private void planeBullet() {

        if(planeBullets.size()==0){ //checking if there is no bullet than spawn a new bullet
            planeBulletPoint = new Point(plane.getxPos() + 100, plane.getyPos() + 100); //Getting the new coordinates and assigning to the ship bullet
            planeBullets.add(new PlaneBullet(new Rect(0, 0, 30, 30), planeBulletPoint, getContext(), bird)); //Adding a new object of ship bullet
        }

        //To declare the bullet dead if it intersects and reduce the bird health. Sound effect on collision
        for (PlaneBullet planeBullet : planeBullets) {
            if (Rect.intersects(bird.getRectangle(), planeBullet.getRectangle())) { //Checking if any element of the array list collides
                planeBullet.setDead(true); //Setting the bullet is dead
                bird.setHealth(bird.getHealth() - planeBullet.getBirdDamage()); //Setting new health to the bird
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

        planeBullets.removeIf(planeBullet -> (planeBullet.isDead())); //Checking if the bullet is dead if so we gonna remove it
    }

    private void shipBullet() {

        //To spawn the bullet
        if (shipBulletSpawnTimer == 200) { //Timer condition

            shipBulletPoint.set(ship.getxPos() + 150, ship.getyPos() + 110); //Getting the new coordinates and assigning to the ship bullet
            shipBullets.add(new ShipBullet(new Rect(0, 0, 30, 30), shipBulletPoint, getContext())); //Adding a new object of ship bullet
            shipBulletSpawnTimer = 0; //Resetting the timer

        } else {
            shipBulletSpawnTimer++;
        }

        //To declare the bullet dead if it intersects and reduce the bird health. Sound effect on collision
        for (ShipBullet shipBullet : shipBullets) {
            if (Rect.intersects(bird.getRectangle(), shipBullet.getRectangle())) { //Checking if any element of the array list collides
                shipBullet.setDead(true); //Setting the bullet is dead
                bird.setHealth(bird.getHealth() - shipBullet.getBirdDamage()); //Setting new health to the bird
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

        shipBullets.removeIf(shipBullet -> (shipBullet.isDead())); //Checking if the bullet is dead if so we gonna remove it
        shipBullets.removeIf(shipBullet -> (shipBullet.getyPos() > 790));//When the bullet will cross that bound it will be removed from the array list.
    }


    private void scoreBird() {

        //This condition makes sure that the score count doesn't go above 9 and it forces the player to drop the items
        if (scoreItem.getScoreCount() < 9) {
            // Checking if the bird is colliding with the item. If so then update item position and score count
            if (Rect.intersects(bird.getRectangle(), scoreItem.getRectangle())) {
                scoreItem.updateMovement();
                scoreItem.setScoreCount(scoreItem.getScoreCount() + 1);

                //Setting up bird eating sound
                //It will only get triggered if the bird has collected any food but must be more than 0
                int setVolume = musicVolume; //Choosing volume amount
                final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
                birdItemSound.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
                if (musicOnOff) {
                    birdItemSound.start();
                }
            }

            //Checking if the bird is in a specific area that is the tree. If so remove score count and add to the score display
            if ((bird.getxPos() > 1338 && bird.getxPos() < 1458) && (bird.getyPos() > 594 && bird.getyPos() < 672)) {

                if (scoreItem.getScoreCount() != 0) {
                    scoreItem.setScoreCount(scoreItem.getScoreCount() - 1);
                    scoreItem.setScoreDisplayCount(scoreItem.getScoreDisplayCount() + 1);

                    //Setting up bird eating sound
                    //It will only get triggered if the bird has collected any food but must be more than 0
                    int setVolume = musicVolume; //Choosing volume amount
                    final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
                    birdEatSound.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
                    if (musicOnOff) {
                        birdEatSound.start();
                    }
                }


            }
        } else {
            //Checking if the bird is in a specific area that is the tree. If so remove score count and add to the score display
            if ((bird.getxPos() > 1338 && bird.getxPos() < 1458) && (bird.getyPos() > 594 && bird.getyPos() < 672)) {

                if (scoreItem.getScoreCount() != 0) {
                    scoreItem.setScoreCount(scoreItem.getScoreCount() - 1);
                    scoreItem.setScoreDisplayCount(scoreItem.getScoreDisplayCount() + 1);

                    //Setting up bird eating sound
                    int setVolume = musicVolume; //Choosing volume amount
                    final float volume = (float) (1 - (Math.log(100 - setVolume) / Math.log(100))); //formula for int to volume conversion in form of float
                    birdEatSound.setVolume(volume, volume); //Setting up the volume . The range of the setVolume method is from 0.0f to 1.0f
                    if (musicOnOff) {
                        birdEatSound.start();
                    }
                }

            }
        }

    }


    /**
     * MY METHODS END
     */


    //These methods are same to the default activity run cycle that saad explained

    /**
     * This is called immediately after any structural changes (format or size) have been made to the surface
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    /**
     * This is called immediately after the surface is first created.
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);


        thread.setRunning(true);
        thread.start();
    }


    /**
     * This is called immediately before a surface is being destroyed.
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }


    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //In order to move any object this is the method that will detect any touch on the surfaceView
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                //This condition checks the bird health and when it becomes less then 0 triggers the game over
                if (bird.getHealth() > 0) {
                    //Method in the Player class to the drone
                    bird.movement(event);
                } else {
                    Intent intent = new Intent(this.getContext(), GameOver.class);
                    this.getContext().startActivity(intent);
                    ((Activity) this.getContext()).finish();
                }

                break;
        }

        //By making it true it will detect all the touches
        return true;


    }


    public void update() {

        bird.update();
        ship.update();
        plane.update();

        //Ship bullet update
        for (ShipBullet shipBullet : shipBullets) {
            shipBullet.update();
        }

        for (PlaneBullet planeBullet : planeBullets) {
            planeBullet.update();
        }

        //Self made methods
        scoreBird();
        shipBullet();
        planeBullet();
    }


    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(canvasBackground, 0, 0, null);

        bird.draw(canvas);
        ship.draw(canvas);
        plane.draw(canvas);

        scoreItem.draw(canvas);

        cloudOne.draw(canvas);
        cloudTwo.draw(canvas);
        cloudThree.draw(canvas);

        for (ShipBullet shipBullet : shipBullets) {
            shipBullet.draw(canvas);
        }

        for (PlaneBullet planeBullet : planeBullets) {
            planeBullet.draw(canvas);
        }

        land.draw(canvas);

    }

}
