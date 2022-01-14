package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Land implements GameObject {

    private Bitmap land[] = new Bitmap[20];



    /**
     * @param context
     */
    public Land(Context context) {
        land[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.w1);
        land[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.w2);
        land[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.w3);
        land[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.w4);
        land[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        land[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        land[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);
        land[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tree);
        land[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bush);
    }

    /**
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        //Setting up the water sprite
        //                            This method allows to scale the image size
        Bitmap resizedBitmap0 = Bitmap.createScaledBitmap(land[0], 80, 40, true);
        Bitmap resizedBitmap1 = Bitmap.createScaledBitmap(land[1], 80, 40, true);
        Bitmap resizedBitmap2 = Bitmap.createScaledBitmap(land[2], 80, 40, true);
        Bitmap resizedBitmap3 = Bitmap.createScaledBitmap(land[3], 80, 40, true);

        //drawing the Bitmap on to the canvas
        canvas.drawBitmap(resizedBitmap0, 0, 770, null);
        canvas.drawBitmap(resizedBitmap1, 80, 770, null);
        canvas.drawBitmap(resizedBitmap2, 160, 770, null);
        canvas.drawBitmap(resizedBitmap3, 240, 770, null);
        canvas.drawBitmap(resizedBitmap0, 320, 770, null);
        canvas.drawBitmap(resizedBitmap1, 400, 770, null);
        canvas.drawBitmap(resizedBitmap2, 480, 770, null);
        canvas.drawBitmap(resizedBitmap3, 560, 770, null);
        canvas.drawBitmap(resizedBitmap0, 640, 770, null);
        canvas.drawBitmap(resizedBitmap1, 720, 770, null);
        canvas.drawBitmap(resizedBitmap2, 800, 770, null);


        //setting up the terrain sprite
        Bitmap resizedBitmap4 = Bitmap.createScaledBitmap(land[4], 80, 70, true);
        Bitmap resizedBitmap5 = Bitmap.createScaledBitmap(land[5], 80, 70, true);
        Bitmap resizedBitmap6 = Bitmap.createScaledBitmap(land[6], 80, 70, true);

        //drawing the terrain block on the canvas
        canvas.drawBitmap(resizedBitmap4, 871, 750, null);
        canvas.drawBitmap(resizedBitmap5, 940, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1010, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1080, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1150, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1220, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1290, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1360, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1430, 750, null);
        canvas.drawBitmap(resizedBitmap5, 1500, 750, null);
        canvas.drawBitmap(resizedBitmap6, 1570, 750, null);


        //setting up the size of the tree
        Bitmap resizedBitmap7 = Bitmap.createScaledBitmap(land[7], 200, 300, true);

        //drawing the tree on the canvas
        canvas.drawBitmap(resizedBitmap7, 1300, 480, null);


        //setting up the size of the tree
        Bitmap resizedBitmap8 = Bitmap.createScaledBitmap(land[8], 60, 35, true);

        //drawing the tree on the canvas
        canvas.drawBitmap(resizedBitmap8, 1330, 730, null);


    }

    @Override
    public void update() {

    }
}
