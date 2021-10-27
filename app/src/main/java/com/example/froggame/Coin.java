package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;

public class Coin extends GameObject{
    //index
    Handler handler;
    Runnable runnable;

    //index
    Platform currentplatform;
    android.graphics.Bitmap Bitmap;
    int Turn = 3;
    boolean HadEvent = false;

    public Coin()
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

        if(HadEvent)
        {
            if(currentplatform.itemtype != Platform.platformType.coin)
            {
                Turn = 3;
                currentplatform.ResetCoinEvent();
                return;
            }
            if(Turn == 0)
            {
                Turn = 3;
                currentplatform.ResetCoinEvent();
            }
            else
            {
                Turn--;
            }
        }
    }
}
