package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class Plane extends Position implements GameObject {

    private Rect rectangle;
    private Bitmap planeSprite[] = new Bitmap[2];
    private Context context;

    private int speed = 4;
    private int directionForSprite=+1;





    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     * @param rectangle
     * @param point
     */
    public Plane(Rect rectangle, Point point, Context context) {
        this.rectangle = rectangle;
        this.setxPos(point.x);
        this.setyPos(point.y);

        this.context = context;

        this.setxVel(speed);

        //Setting up the birdSprites
        planeSprite[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.planel);
        planeSprite[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.planer);



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
        Bitmap resizedBitmap0 = Bitmap.createScaledBitmap(planeSprite[0], 200, 100, true);
        Bitmap resizedBitmap1 = Bitmap.createScaledBitmap(planeSprite[1], 200, 100, true);

        //drawing the Bitmap on to the canvas
        if(directionForSprite==+1){
            canvas.drawBitmap(resizedBitmap1, this.getxPos(), this.getyPos(), null);
        }
        if(directionForSprite==-1){
            canvas.drawBitmap(resizedBitmap0, this.getxPos(), this.getyPos(), null);
        }



    }


    //TODO fix the motion of the ship to move in a set
    public void movement(){

        if(this.getxPos()>=1400){
            this.setxVel(-speed);
            directionForSprite=-1;
        }
        if(this.getxPos()<=60){
            this.setxVel(+speed);
            directionForSprite=+1;
        }
        updateMovement(this.getxVel(), 0);

    }


    @Override
    public void update() {
        movement();
    }


    /**
     * I just had to (getXPos() - rectangle.width() / 2)+velX  if I do it the way I code in java fx
     */
    public void updateMovement(int velX, int velY) {

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

}
