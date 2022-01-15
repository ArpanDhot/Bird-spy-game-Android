package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ShipBullet extends Position implements GameObject {

    private Rect rectangle;
    private Bitmap shipBulletSprite;
    private Context context;

    private int speed = 4;
    private boolean dead = false;
    private int birdDamage = 5;
    private int timer = 0;//This is going to be used so after certain amount of hits the ball is going to fall


    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     *
     * @param rectangle
     * @param point
     */
    public ShipBullet(Rect rectangle, Point point, Context context) {
        this.rectangle = rectangle;
        this.setxPos(point.x);
        this.setyPos(point.y);

        this.context = context;

        this.setxVel(speed);
        this.setyVel(-speed);

        //Setting up the birdSprites
        shipBulletSprite = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.cannonball);

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //This method allows to scale the image size
        Bitmap resizedBitmap0 = Bitmap.createScaledBitmap(shipBulletSprite, 30, 30, true);

        //Drawing the ball
        //I am subtracting 15(width and height if the sprite) from x and y pos. The reason is to align the rectangle with the sprite so the collision will work exactly right visually.
        canvas.drawBitmap(resizedBitmap0, this.getxPos()-15, this.getyPos()-15, null);
    }



    public void movement() {

        if (timer < 6) { //If tou want to increase the life of the bullet just simply increase the integer value
            //Condition are checking the bound and make bullet bounce
            if (this.getxPos() >= 1600) {
                this.setxVel(-speed);
                timer++;
            }
            if (this.getxPos() <= 0) {
                this.setxVel(+speed);
                timer++;
            }

            if (this.getyPos() >= 750) {
                this.setyVel(-speed);
                timer++;
            }
            if (this.getyPos() <= 0) {
                this.setyVel(+speed);
                timer++;
            }
            updateMovement(this.getxVel(), this.getyVel());
        } else {
            //It will make the bullet fall down and then once it passes the 760 mark it will make the bullet dead. Then we can simply chek in the GameView and remove it from the linked list
            this.setxVel(0);
            this.setyVel(+speed);
            updateMovement(this.getxVel(), this.getyVel());
            if (this.getyPos() > 760) {
                dead = true;
            }
        }
    }


    @Override
    public void update() {
        movement();
    }


    /**
     * To update the position of the object as well as its shape
     */
    public void updateMovement(int velX, int velY) {

        this.setxPos(this.getxPos() + velX);
        this.setyPos(this.getyPos() + velY);
        rectangle.set((this.getxPos() - rectangle.width() / 2) , (this.getyPos() - rectangle.height() / 2), (this.getxPos() + rectangle.width() / 2), (this.getyPos() + rectangle.height() / 2));

    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rect rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getBirdDamage() {
        return birdDamage;
    }

    public void setBirdDamage(int birdDamage) {
        this.birdDamage = birdDamage;
    }
}