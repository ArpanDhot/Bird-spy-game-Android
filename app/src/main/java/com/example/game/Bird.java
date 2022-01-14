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
    private Context context;

    private int health=100;
    private double OldXPos = 0;
    private double OldYPos = 0;
    private int Speed = 6;





    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
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

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //Setting up the rectangle color and drawing it
        Paint paint = new Paint();
        paint.setColor(Color.rgb(55,55,55));
        canvas.drawRect(rectangle, paint);

        //Printing the heart
        Bitmap resizedBitmap0 = Bitmap.createScaledBitmap(birdHealth, 110, 100, true);
        canvas.drawBitmap(resizedBitmap0,30,30,null);

        canvas.drawText(" "+health,40,90,paintText);

    }


    //TODO set the bounds so the bird will not go out
    /**
     * @param event
     */
    public void movement(MotionEvent event){

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
        }
        if (xPos <= OldXPos) { //Checking if the player goes out of bound
            this.setxVel(-Speed);
        }


//                if (yPos >= snakeOldYPos && player.getyPos() <= 1000) {
//                    player.setyVel(+snakeSpeed);
//                }
//                if (yPos <= snakeOldYPos && player.getyPos() >= 70) { //Checking if the player goes out of bound
//                    player.setyVel(-snakeSpeed);
//                }
//
//                if (xPos >= snakeOldXPos && player.getxPos() <= 1000) {
//                    player.setxVel(+snakeSpeed);
//                }
//                if (xPos <= snakeOldXPos && player.getxPos() >= 70) { //Checking if the player goes out of bound
//                    player.setxVel(-snakeSpeed);
//                }

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

            this.setxPos(this.getxPos()+velX);
            this.setyPos(this.getyPos()+velY);
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