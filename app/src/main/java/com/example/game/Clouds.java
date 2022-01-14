package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;

import java.util.Random;

public class Clouds extends Position implements GameObject {

    private Bitmap cloud[] = new Bitmap[5];
    private Random random;
    private int cloudType;


    /**
     * @param context
     * @param cloudType
     */
    public Clouds(Context context, int cloudType) {
        cloud[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.c1);
        cloud[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.c2);
        random = new Random();
        this.cloudType = cloudType;
        resetPosition();
    }


    public void resetPosition() {

        if (cloudType == 1) {
            this.setxPos(2000); //reset value from right to left
        } else {
            this.setxPos(-300); //reset value from the left to right
        }

        this.setyPos(random.nextInt(200)); //adding random y coordinate on each spawn
        this.setxVel(2 + random.nextInt(5)); //setting the velocity have a base value as 2 if zero come the lowest speed is going to be 2 and max is 2+5
    }


    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        //                            This method allows to scale the image size
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(cloud[cloudType], 250, 100, true);
        //drawing the Bitmap on to the canvas
        canvas.drawBitmap(resizedBitmap, this.getxPos(), this.getyPos(), null);

        //if condition is 1 then the clouds will come from the right hand side else from the left
        if (cloudType == 1) {
            this.setxPos(this.getxPos() - this.getxVel());
            if (this.getxPos() < -300) {//cloud width
                resetPosition(); //Once the end is reached the values are restart to where it have to start again
            }
        } else {
            this.setxPos(this.getxPos() + this.getxVel());
            if (this.getxPos() > 2000) {//activity width
                resetPosition(); //Once the end is reached the values are restart to where it have to start again
            }
        }
    }


    @Override
    public void update() {

    }
}
