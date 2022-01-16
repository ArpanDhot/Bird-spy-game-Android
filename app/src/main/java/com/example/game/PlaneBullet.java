package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class PlaneBullet extends Position implements GameObject {

    private Rect rectangle;
    private Bitmap planeBulletSprite[] = new Bitmap[2];
    private Context context;
    private Bird bird;


    private double oldXPos = 0;
    private double oldYPos = 0;
    private int speed = 1;
    private boolean dead = false;
    private int birdDamage = 5;


    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     *
     * @param rectangle
     * @param point
     */
    public PlaneBullet(Rect rectangle, Point point, Context context, Bird bird) {
        this.rectangle = rectangle;
        this.setxPos(point.x);
        this.setyPos(point.y);

        this.context = context;

        //Taking the bird class and storing it into this class so I can access all the elements of the bird class.
        //Theoretically each class is stored in form of pointer in their corresponded variables.
        // Therefore as long as you go the memory address they will be the same no matter in which class you got them.
        this.bird = bird;

        this.setxVel(speed);
        this.setyVel(-speed);

        //Setting up the birdSprites
        planeBulletSprite[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pbl);
        planeBulletSprite[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pbr);

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {


        Bitmap resizedBitmap0 = null;

        //This method allows to scale the image size
        double xPos = this.getxPos();
        if (oldXPos > xPos) {
            resizedBitmap0 = Bitmap.createScaledBitmap(planeBulletSprite[0], 60, 50, true);
        } else {
            resizedBitmap0 = Bitmap.createScaledBitmap(planeBulletSprite[1], 60, 50, true);
        }

        //Drawing the ball
        //I am subtracting 15(width and height if the sprite) from x and y pos. The reason is to align the rectangle with the sprite so the collision will work exactly right visually.
        canvas.drawBitmap(resizedBitmap0, this.getxPos() - 15, this.getyPos() - 15, null);

        oldXPos = this.getxPos();

    }


    public void movement() {

        if (this.getxPos() >= bird.getxPos()) {
            this.setxVel(-speed);
        } else {
            this.setxVel(+speed);
        }

        if (this.getyPos() >= bird.getyPos()) {
            this.setyVel(-speed);
        } else {
            this.setyVel(+speed);
        }

        updateMovement(this.getxVel(), this.getyVel());
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
        rectangle.set((this.getxPos() - rectangle.width() / 2), (this.getyPos() - rectangle.height() / 2), (this.getxPos() + rectangle.width() / 2), (this.getyPos() + rectangle.height() / 2));

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