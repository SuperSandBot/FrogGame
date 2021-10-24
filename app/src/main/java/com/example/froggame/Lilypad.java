package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Lilypad extends GameObject {

    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    public Lilypad()
    {

    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(getBm(),this.x,this.y,null);
    }

    public Bitmap getBm()
    {
        return this.getBitmaps().get(0);
    }

    public ArrayList<Bitmap> getBitmaps()
    {
        return this.Bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> Bitmaps)
    {
        this.Bitmaps = Bitmaps;
        for(int i = 0 ; i < Bitmaps.size() ; i++)
        {
            this.Bitmaps.set(i,Bitmap.createScaledBitmap(Bitmaps.get(i),this.Width,this.Height,true));
        }
    }
}
