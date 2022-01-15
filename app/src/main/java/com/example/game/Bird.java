package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;

import java.util.Timer;

public class Bird extends Position implements GameObject {

    private Rect rectangle;
    private Paint paintText;
    private Bitmap birdHealth;
    private Bitmap birdSpriteLeft[] = new Bitmap[4];
    private Bitmap birdSpriteRight[] = new Bitmap[4];
    private Context context;

    private int health = 100;
    private double OldXPos = 0;
    private double OldYPos = 0;
    private int Speed = 6;
    private int directionForSprite = +1;


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


        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(40);

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

        //
        SystemClock.sleep(50);
    }

    int i = 0;

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        Bitmap loadingSprite = null;

        //drawing the Bitmap on to the canvas
        if (directionForSprite == -1) {
            loadingSprite = Bitmap.createScaledBitmap(birdSpriteRight[i++], 80, 60, true);
        } else if (directionForSprite == +1) {
            loadingSprite =  Bitmap.createScaledBitmap(birdSpriteLeft[i++], 80, 60, true);
        }

        //Sprite count reset
        if (i == 4){
            i = 0;
        }

        //drawing the Bitmap on to the canvas
        canvas.drawBitmap(loadingSprite, this.getxPos() - 35, this.getyPos() - 25, null);

        //Printing the heart
        Bitmap resizedBitmap3 = Bitmap.createScaledBitmap(birdHealth, 110, 100, true);
        canvas.drawBitmap(resizedBitmap3, 30, 30, null);
        canvas.drawText(" " + health, 40, 90, paintText);
    }


    /**
     * @param event
     */
    public void movement(MotionEvent event) {

        double yPos = event.getY();
        double xPos = event.getX();

        //Checking if the older values of the y-axis was greater and current value is smaller that means velocity must be subtracted and if its vice versa I must increase it
        if (yPos >= OldYPos) {
            this.setyVel(+Speed);
        }
        if (yPos <= OldYPos) { //Checking if the player goes out of bound
            this.setyVel(-Speed);
        }

        if (xPos >= OldXPos) {
            this.setxVel(+Speed);
            directionForSprite = +1;
        }
        if (xPos <= OldXPos) { //Checking if the player goes out of bound
            this.setxVel(-Speed);
            directionForSprite = -1;
        }

        //storing the older values to compare it in the conditions
        OldXPos = event.getX();
        OldYPos = event.getY();
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
}