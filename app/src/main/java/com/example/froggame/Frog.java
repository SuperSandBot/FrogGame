package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Frog extends GameObject {
    ArrayList<Bitmap> Bitmaps = new ArrayList<>();
    Platform currentplatform;
    public Frog()
    {
        super();
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(getBm(),this.x,this.y,null);
    }

    @Override
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
            this.Bitmaps.set(i,Bitmap.createScaledBitmap(this.Bitmaps.get(i),this.Width,this.Height,true));
        }
    }

    public void move(Platform platform)
    {
        currentplatform = platform;
        this.setX(platform.getX());
        this.setY(platform.getY()-40);
    }

}
