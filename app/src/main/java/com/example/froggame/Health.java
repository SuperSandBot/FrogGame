package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Health extends GameObject{

    //index
    private int MaxHeart = 8;
    public int CurrentHeart = 8;
    Bitmap Bitmap;

    public Health()
    {

    }
    public void draw(Canvas canvas)
    {
        int y = 0;
        boolean bool = true;
        for (int i = 0; i < CurrentHeart; i++, y++)
        {
            canvas.drawBitmap(Bitmap,this.x + 100 * y,this.y,null);
        }
    }

    public void setBitmap(Bitmap Bitmap)
    {
        this.Bitmap = android.graphics.Bitmap.createScaledBitmap(Bitmap,this.Width,this.Height,true);
    }

    public void Heal(int health)
    {
        CurrentHeart += health;
        if(CurrentHeart > MaxHeart)
        {
            CurrentHeart = MaxHeart;
        }
    }

    public void takeDamge()
    {
        CurrentHeart--;
    }
}
