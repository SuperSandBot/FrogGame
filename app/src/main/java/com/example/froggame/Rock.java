package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;

public class Rock extends GameObject{

    //index
    Handler handler;
    Runnable runnable;

    //index
    Platform currentplatform;
    Bitmap Bitmap;
    int Turn = 8;
    boolean HadEvent = false;

    public Rock()
    {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                StartEvent();
            }
        };
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(getBm(),this.x,this.y,null);
    }

    public Bitmap getBm()
    {
        return this.Bitmap;
    }

    public void setBitmap(Bitmap Bitmap)
    {
        this.Bitmap = Bitmap.createScaledBitmap(Bitmap,this.Width,this.Height,true);
    }
    public void StartEvent()
    {
        if(HadEvent) {
            switch (Turn) {
                case 8: {
                    currentplatform.lilypad.currentImage = 2;
                    currentplatform.platformtype = Platform.platformType.lilypad;
                    break;
                }
                case 7: {
                    currentplatform.lilypad.currentImage = 3;
                    currentplatform.platformtype = Platform.platformType.lilypad;
                    break;
                }
                case 6: {
                    currentplatform.lilypad.currentImage = 0;
                    currentplatform.platformtype = Platform.platformType.nothing;
                    break;
                }
                case 5:
                {
                    currentplatform.platformtype = Platform.platformType.rock;
                    break;
                }
                case 1: {
                    currentplatform.platformtype = Platform.platformType.nothing;
                    break;
                }
                case 0: {
                    Turn = 5;
                    currentplatform.ResetRockEvent();
                    break;
                }
            }
            Turn--;
        }
    }
}
