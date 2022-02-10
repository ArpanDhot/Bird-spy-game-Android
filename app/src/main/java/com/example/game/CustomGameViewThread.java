package com.example.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CustomGameViewThread extends Thread {

    public static final int MAX_FPS = 30; //Capping the FPS to max 30
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private CustomGameView CustomGameView;
    private boolean running;
    public static Canvas canvas;


    /**
     * @param surfaceHolder
     * @param CustomGameView
     */
    public CustomGameViewThread(SurfaceHolder surfaceHolder, CustomGameView CustomGameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.CustomGameView = CustomGameView;
    }


    // The run method is part of the run class
    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        int totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.CustomGameView.update();//update the objects position
                    this.CustomGameView.draw(canvas);//draw the object position
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime>0){
                    this.sleep(waitTime);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS){
                averageFPS= 1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                System.out.println(averageFPS);
            }
        }
    }

    /**
     * @param running
     */
    public void setRunning(boolean running){
        this.running = running;
    }

}
