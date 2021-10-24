package com.example.froggame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class GameObject {

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int Width) {
        this.Width = Width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public Rect getRect()
    {
        return new Rect((int)this.x,(int)this.y,(int)this.x + Width ,(int)this.y + Height);
    }

    protected float x,y;
    protected int Width , Height;

    public GameObject()
    {

    }

    public GameObject(float x, float y, int Width, int Height) {
        this.x = x;
        this.y = y;
        this.Width = Width;
        this.Height = Height;
    }
}
