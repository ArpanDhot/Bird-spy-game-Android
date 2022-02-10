package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class LevelCreate extends View {

    final long UPDATE_MILLIS = 30;
    private Handler handler;
    private Runnable runnable;

    //Touch detection rectangle
    private Point touchFollowPoint;
    private Rect touchFollow;

    //Block
    private Point blockPoint;
    private ArrayList<Block> blocks = new ArrayList<>();
    private int spriteRectSize = 50;

    //Button to place items
    private Point buttonPlaceItemPoint;
    private Rect buttonPlaceItem;
    private boolean buttonPlaceItemPressed = false;
    private int buttonPlaceItemClickCount = 0;

    //Button to increase the block size
    private Point buttonIncreaseSizePoint;
    private Rect buttonIncreaseSize;
    private boolean buttonIncreaseSizePressed = false;
    private int buttonIncreaseSizeClickCount = 0;

    //Button to decrease the block size
    private Point buttonDecreaseSizePoint;
    private Rect buttonDecreaseSize;
    private boolean buttonDecreaseSizePressed = false;
    private int buttonDecreaseSizeClickCount = 0;

    //Button to save
    private Point buttonSaveItemPoint;
    private Rect buttonSaveItem;
    private boolean buttonSaveItemPressed = false;
    private int buttonSaveItemClickCount = 0;

    private Paint textStyle;


    public LevelCreate(Context context) {

        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        textStyle = new Paint();
        textStyle.setColor(Color.BLACK);
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setTextSize(40);

        //The touchFollowPoint(rectangle) will be used to get intersection against the save and the put "button". This approach was better than the conditions to create the perimeter because then I could not delay and the code would have been long.
        touchFollowPoint = new Point(500, 500);
        touchFollow = new Rect(0, 0, 40, 40);
        touchFollow.set(touchFollowPoint.x - touchFollow.width() / 2, touchFollowPoint.y - touchFollow.height() / 2, touchFollowPoint.x + touchFollow.width() / 2, touchFollowPoint.y + touchFollow.height() / 2);

        //The buttonPad (rectangle) will be used as button to put in the items
        //BUTTON LOGIC STARTS FROM LINE 116
        buttonPlaceItemPoint = new Point(1500, 700);
        buttonPlaceItem = new Rect(0, 0, 150, 150);
        buttonPlaceItem.set(buttonPlaceItemPoint.x - buttonPlaceItem.width() / 2, buttonPlaceItemPoint.y - buttonPlaceItem.height() / 2, buttonPlaceItemPoint.x + buttonPlaceItem.width() / 2, buttonPlaceItemPoint.y + buttonPlaceItem.height() / 2);

        //Button to increase the block size
        buttonIncreaseSizePoint = new Point(1540, 350);
        buttonIncreaseSize = new Rect(0, 0, 80, 80);
        buttonIncreaseSize.set(buttonIncreaseSizePoint.x - buttonIncreaseSize.width() / 2, buttonIncreaseSizePoint.y - buttonIncreaseSize.height() / 2, buttonIncreaseSizePoint.x + buttonIncreaseSize.width() / 2, buttonIncreaseSizePoint.y + buttonIncreaseSize.height() / 2);

        //Button to decrease the block size
        buttonDecreaseSizePoint = new Point(1540, 450);
        buttonDecreaseSize = new Rect(0, 0, 80, 80);
        buttonDecreaseSize.set(buttonDecreaseSizePoint.x - buttonDecreaseSize.width() / 2, buttonDecreaseSizePoint.y - buttonDecreaseSize.height() / 2, buttonDecreaseSizePoint.x + buttonDecreaseSize.width() / 2, buttonDecreaseSizePoint.y + buttonDecreaseSize.height() / 2);

        //Button to save
        buttonSaveItemPoint = new Point(1500, 100);
        buttonSaveItem = new Rect(0, 0, 150, 150);
        buttonSaveItem.set(buttonSaveItemPoint.x - buttonSaveItem.width() / 2, buttonSaveItemPoint.y - buttonSaveItem.height() / 2, buttonSaveItemPoint.x + buttonSaveItem.width() / 2, buttonSaveItemPoint.y + buttonSaveItem.height() / 2);

        //Creating the first block object to avoid to have any index issues
        blockPoint = new Point(50, 50);
        blocks.add(new Block(new Rect(0, 0, spriteRectSize, spriteRectSize), blockPoint, getContext(), spriteRectSize));


    }


    private void update() {

        //2) I am checking if the click count is equal to 2 then trigger and reset the count ...LINE 128
        //To place the block
        if (buttonPlaceItemClickCount == 2) {

            //Creating the locks on when the user clicks
            blockPoint = new Point(50, 50);
            blocks.add(new Block(new Rect(0, 0, spriteRectSize, spriteRectSize), blockPoint, getContext(), spriteRectSize));
            //Reset the count of click so the cycle continues
            buttonPlaceItemClickCount = 0;
        }

        //To increase the size of the block
        if (buttonIncreaseSizeClickCount == 2) {
            //Increasing the size of the sprite
            spriteRectSize += 10;
            //Updating the size of the sprite to the objects variable
            blocks.get(blocks.size() - 1).setSpriteSize(spriteRectSize);
            //Updating the size of the rectangle
            blocks.get(blocks.size() - 1).getRectangle().set(0, 0, spriteRectSize, spriteRectSize);
            //Resetting the click count
            buttonIncreaseSizeClickCount = 0;
        }

        //To decrease the size of the block
        if (buttonDecreaseSizeClickCount == 2) {
            spriteRectSize -= 10;
            blocks.get(blocks.size() - 1).setSpriteSize(spriteRectSize);
            blocks.get(blocks.size() - 1).getRectangle().set(0, 0, spriteRectSize, spriteRectSize);
            buttonDecreaseSizeClickCount = 0;
        }

        //To save the size of the block
        if (buttonSaveItemClickCount == 2) {
            System.out.println("SAVE");
            buttonSaveItemClickCount = 0;
        }

    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        //Printing a of the block objects
        for (Block block : blocks) {
            block.draw(canvas);
        }

        //Printing the place button rectangle on the screen
        Paint buttonPlaceItemPaint = new Paint();
        buttonPlaceItemPaint.setColor(Color.argb(30, 128, 128, 128));
        canvas.drawRect(buttonPlaceItem, buttonPlaceItemPaint);
        canvas.drawText("Place", 1450, 715, textStyle);

        //Printing the increase size button rectangle on the screen
        Paint buttonIncreaseSizePaint = new Paint();
        buttonIncreaseSizePaint.setColor(Color.argb(30, 128, 128, 128));
        canvas.drawRect(buttonIncreaseSize, buttonIncreaseSizePaint);
        canvas.drawText("+", 1530, 365, textStyle);

        //Printing the decrease size button rectangle on the screen
        Paint buttonDecreaseSizePaint = new Paint();
        buttonDecreaseSizePaint.setColor(Color.argb(30, 128, 128, 128));
        canvas.drawRect(buttonDecreaseSize, buttonDecreaseSizePaint);
        canvas.drawText("_", 1530, 450, textStyle);

        //Printing the save button rectangle on the screen
        Paint buttonSaveItemPaint = new Paint();
        buttonSaveItemPaint.setColor(Color.argb(30, 128, 128, 128));
        canvas.drawRect(buttonSaveItem, buttonSaveItemPaint);
        canvas.drawText("Save", 1460, 110, textStyle);


//        Paint paint1 = new Paint();
//        paint1.setColor(Color.argb(30, 128, 128, 128));
//        canvas.drawRect(touchFollow, paint1);


//            if(life == 0){
//                Intent intent = new Intent(context, GameOver.class);
//                intent.putExtra("points", points);
//                context.startActivity(intent);
//                ((Activity)context).finish();
//            }

        update();
        handler.postDelayed(runnable, UPDATE_MILLIS);
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
                //1) I am checking if the touchFollow is intersection with the buttonPad.Then assigning the trigger to the boolean...LINE 59
                //Button place trigger
                if (Rect.intersects(touchFollow, buttonPlaceItem)) {
                    buttonPlaceItemPressed = true;
                    if (buttonPlaceItemPressed == true) {
                        buttonPlaceItemClickCount++;
                    }
                }


                //Button increase size trigger
                if (Rect.intersects(touchFollow, buttonIncreaseSize)) {
                    buttonIncreaseSizePressed = true;
                    if (buttonIncreaseSizePressed == true) {
                        buttonIncreaseSizeClickCount++;
                    }
                }

                //Button decrease size trigger
                if (Rect.intersects(touchFollow, buttonDecreaseSize)) {
                    buttonDecreaseSizePressed = true;
                    if (buttonDecreaseSizePressed == true) {
                        buttonDecreaseSizeClickCount++;
                    }
                }

                //Button to save trigger
                if (Rect.intersects(touchFollow, buttonSaveItem)) {
                    buttonSaveItemPressed = true;
                    if (buttonSaveItemPressed == true) {
                        buttonSaveItemClickCount++;
                    }
                }

            case MotionEvent.ACTION_MOVE:

                //Updating the position of the touchFollow rectangle. It is going to have the exact pos where is pressed.
                touchFollow.set((int) event.getX() - touchFollow.width() / 2, (int) event.getY() - touchFollow.height() / 2, (int) event.getX() + touchFollow.width() / 2, (int) event.getY() + touchFollow.height() / 2);

                //Only calling the block movement method of the most recent element
                //The condition is there to stop the block to move when the user is pressing the button
                if (!(Rect.intersects(touchFollow, buttonPlaceItem) || Rect.intersects(touchFollow, buttonIncreaseSize)) || (Rect.intersects(touchFollow, buttonDecreaseSize) || Rect.intersects(touchFollow, buttonSaveItem))) {
                    blocks.get(blocks.size() - 1).movement(event);
                }

            case MotionEvent.ACTION_UP:
                //Closing down the button when the the finger is lifted from the screen.
                buttonPlaceItemPressed = false;
                buttonIncreaseSizePressed = false;
                buttonDecreaseSizePressed = false;
                buttonSaveItemPressed = false;

                break;
        }
        return true;
    }


}