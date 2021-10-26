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
    boolean isAted = false;

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
        if(isAted)
        {
            currentplatform.ResetGoodEvent();
        }
        else
        {
            isAted = true;
            currentplatform.itemtype = Platform.platformType.coin;
            handler.postDelayed(runnable,5000);
        }
    }
}
