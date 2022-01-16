package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Bird extends Position implements GameObject {

    private Rect rectangle;
    private Paint paintText;
    private Bitmap birdHealth;
    private Bitmap birdSpriteLeft[] = new Bitmap[4];
    private Bitmap birdSpriteRight[] = new Bitmap[4];
    private Bitmap birdExplosionSprite[] = new Bitmap[7];
    private Context context;

    private int health = 99;
    private double oldXPos = 0;
    private double oldYPos = 0;
    private int speed = 6;
    private int directionForSprite = +1;
    private boolean birdExplosion=false;
    private int birdSpriteIndex = 0;
    private int explosionSpriteIndex=0;


    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     *
     * @param rectangle
     * @param point
     */
    public Bird(Rect rectangle, Point point, Context context) {
        this.rectangle = rectangle;
        this.setxPos(point.x);
        this.setyPos(point.y);

        this.context = context;

        //Setting up the heart
        birdHealth = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.heart);

        //Setting up the birdSprites
        birdSpriteLeft[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bl1);
        birdSpriteLeft[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bl2);
        birdSpriteLeft[2] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bl3);
        birdSpriteLeft[3] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.bl4);

        birdSpriteRight[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.br1);
        birdSpriteRight[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.br2);
        birdSpriteRight[2] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.br3);
        birdSpriteRight[3] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.br4);

        //Setting up the bird explosion
        birdExplosionSprite[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e1);
        birdExplosionSprite[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e2);
        birdExplosionSprite[2] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e3);
        birdExplosionSprite[3] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e4);
        birdExplosionSprite[4] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e5);
        birdExplosionSprite[5] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e6);
        birdExplosionSprite[6] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.e7);

        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(40);

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }



    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        Bitmap resizedBirdSprite = null;
        Bitmap resizedExplosionSprite = null;

        //drawing the Bitmap on to the canvas
        if (directionForSprite == -1) {
            resizedBirdSprite = Bitmap.createScaledBitmap(birdSpriteRight[birdSpriteIndex++], 80, 60, true);
        } else if (directionForSprite == +1) {
            resizedBirdSprite =  Bitmap.createScaledBitmap(birdSpriteLeft[birdSpriteIndex++], 80, 60, true);
        }

        //Bird sprite count reset
        if (birdSpriteIndex == 4){
            birdSpriteIndex = 0;
        }

        //drawing the Bitmap on to the canvas
        canvas.drawBitmap(resizedBirdSprite, this.getxPos() - 35, this.getyPos() - 25, null);



        //Printing the heart
        Bitmap resizedBitmap3 = Bitmap.createScaledBitmap(birdHealth, 110, 100, true);
        canvas.drawBitmap(resizedBitmap3, 30, 30, null);
        canvas.drawText(" " + health, 52, 90, paintText);



        //Bird explosion set by the user true or false
        if(birdExplosion){
            //Resizing the explosion sprites
            resizedExplosionSprite = Bitmap.createScaledBitmap(birdExplosionSprite[explosionSpriteIndex++], 80, 80, true); //Post incrementing the index variable that means it will assign the value as it is once it is assigned then it will increment.

            //Bird sprite count reset
            if (explosionSpriteIndex == 7){ //Resetting everything at end of the sprite that is 7
                explosionSpriteIndex = 0; //Resetting the index back to zero so then the next collision will happen the sequence of execution will be right
                birdExplosion =false; //setting back to false when the sprite sequence is ended
            }

            //drawing the Bitmap on to the canvas
            canvas.drawBitmap(resizedExplosionSprite, this.getxPos() - 35, this.getyPos() - 25, null);
        }


    }


    /**
     * @param event
     */
    public void movement(MotionEvent event) {

        double yPos = event.getY();
        double xPos = event.getX();

        //Checking if the older values of the y-axis was greater and current value is smaller that means velocity must be subtracted and if its vice versa I must increase it
        if (yPos >= oldYPos) {
            this.setyVel(+speed);
        }
        if (yPos <= oldYPos) { //Checking if the player goes out of bound
            this.setyVel(-speed);
        }

        if (xPos >= oldXPos) {
            this.setxVel(+speed);
            directionForSprite = +1;
        }
        if (xPos <= oldXPos) { //Checking if the player goes out of bound
            this.setxVel(-speed);
            directionForSprite = -1;
        }

        //storing the older values to compare it in the conditions
        oldXPos = event.getX();
        oldYPos = event.getY();
        this.update(this.getxVel(), this.getyVel());
        this.setyVel(0);
        this.setxVel(0);
    }


    @Override
    public void update() {

    }


    /**
     * I just had to (getXPos() - rectangle.width() / 2)+velX  if I do it the way I code in java fx
     */
    public void update(int velX, int velY) {

        this.setxPos(this.getxPos() + velX);
        this.setyPos(this.getyPos() + velY);
        rectangle.set((this.getxPos() - rectangle.width() / 2) + velX, (this.getyPos() - rectangle.height() / 2) + velY, (this.getxPos() + rectangle.width() / 2) + velX, (this.getyPos() + rectangle.height() / 2) + velY);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isBirdExplosion() {
        return birdExplosion;
    }

    public void setBirdExplosion(boolean birdExplosion) {
        this.birdExplosion = birdExplosion;
    }
}