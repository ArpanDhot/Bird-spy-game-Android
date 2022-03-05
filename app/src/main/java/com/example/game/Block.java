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

import org.json.JSONException;
import org.json.JSONObject;

public class Block extends Position implements GameObject {

    private Rect rectangle;
    private Bitmap planeBulletSprite[] = new Bitmap[2];
    private Context context;


    private double oldXPos = 0;
    private double oldYPos = 0;
    private int speed = 4;
    private int birdDamage = 5;
    private int spriteSize;


    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     *
     * @param rectangle
     * @param point
     */
    public Block(Rect rectangle, Point point, Context context, int spriteSize) {
        this.rectangle = rectangle;
        this.setxPos(point.x);
        this.setyPos(point.y);

        this.context = context;

        //Because we are resizing the block we need the spiteSize to keep the block of size we want
        this.spriteSize = spriteSize;

        this.setxVel(speed);
        this.setyVel(-speed);

        //Setting up the birdSprites
        planeBulletSprite[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.lavablock);

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        Bitmap resizedBitmap0 = Bitmap.createScaledBitmap(planeBulletSprite[0], spriteSize, spriteSize, true);

        //Drawing the block
        //I am subtracting 15(width and height if the sprite) from x and y pos. The reason is to align the rectangle with the sprite so the collision will work exactly right visually.
        canvas.drawBitmap(resizedBitmap0, this.getxPos() - 15, this.getyPos() - 15, null);

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
        }
        if (xPos <= oldXPos) { //Checking if the player goes out of bound
            this.setxVel(-speed);
        }

        //storing the older values to compare it in the conditions
        oldXPos = event.getX();
        oldYPos = event.getY();
        this.update(this.getxVel(), this.getyVel());
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

    public int getBirdDamage() {
        return birdDamage;
    }

    public void setBirdDamage(int birdDamage) {
        this.birdDamage = birdDamage;
    }

    public int getSpriteSize() {
        return spriteSize;
    }

    public void setSpriteSize(int spriteSize) {
        this.spriteSize = spriteSize;
    }

    /**
     * Remouvi questo metodo perche non fa nulla
     *
     * @param n1
     * @param n2
     */
    @Override
    public void updatePos(int n1, int n2) {
        int min;
        if (n1 > n2)
            min = n2;
        else
            min = n1;
    }

    //JSON getter and setter
    public JSONObject getJson(String JSONObj) {
        JSONObject json = new JSONObject();
        //This exception is required because value is not sure if it will be passed
        try {
            json.put("objectName", JSONObj);
            json.put("posX", this.getxPos());
            json.put("posY", this.getxPos());
            json.put("velX", this.getxVel());
            json.put("velY", this.getyVel());
            json.put("spriteSize", this.getSpriteSize());
            json.put("rectangleSize", this.getSpriteSize());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json;
    }

    public void setJson(JSONObject json) {
        try {
            this.setxPos(json.getInt("posX"));
            this.setyPos(json.getInt("posY"));
            this.setxVel(json.getInt("velX"));
            this.setyVel(json.getInt("velY"));
            this.setSpriteSize(json.getInt("spriteSize"));
            this.rectangle.set(0, 0, json.getInt("spriteSize"), json.getInt("spriteSize"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
