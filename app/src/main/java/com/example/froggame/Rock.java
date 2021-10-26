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
    boolean isdying = false;

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
        if(isdying)
        {
            currentplatform.platformtype = Platform.platformType.nothing;
            currentplatform.ResetBadEvent();
        }
        else
        {
            isdying = true;
            currentplatform.platformtype = Platform.platformType.rock;
            handler.postDelayed(runnable,5000);
        }
    }
}
