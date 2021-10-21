package com.example.froggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {

    Frog frog;
    ArrayList<Platform> Platforms;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetupGameView();
        SetupFrog();
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        for (Platform p: Platforms) {
            p.draw(canvas);
        }
        frog.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch(a)
        {
            case MotionEvent.ACTION_DOWN:
            {
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                break;
            }
        }
        return true;
    }

    // khởi tạo 5 * 4 lục bình lên màn hình
    private void SetupGameView()
    {
        Platforms = new ArrayList<>();
        for (int i = 0 ; i < 4 ; i++ )
        {
            for (int y = 0 ; y < 4 ; y++)
            {
                Platform platform = new Platform();
                platform.setX((int)(100 * SCREEN.WIDTH/ 900 + i * 300));
                platform.setY((int)(100 * SCREEN.HEIGHT/ 300 + y * 370));
                platform.Setup(this.getResources());
                Platforms.add(platform);
            }
        }
    }

    //khởi tạo con cóc tại cái lục bình đầu tiên
    private void SetupFrog()
    {
        frog = new Frog();
        frog.setX(Platforms.get(0).getX());
        frog.setY(Platforms.get(0).getY() - 40);
        frog.setWidth(220*SCREEN.WIDTH / SCREEN.WIDTH);
        frog.setHeight(400*SCREEN.HEIGHT / SCREEN.HEIGHT);
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog1));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog2));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog3));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog4));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog5));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog6));
        bitmaps.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.frog7));
        frog.setBitmaps(bitmaps);
    }
}
