package com.example.froggame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    //index
    Platform[][] platforms;
    Frog frog;
    Health health;

    //tool
    Handler handler;
    Runnable runnable;
    Random random = new Random();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //Khởi tạo handler để cho runnable update liên tục.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //chạy lại hàm onDraw
                invalidate();
            }
        };
    }

    //Vẽ hình ảnh lên UI
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        for (int i = 0 ; i < platforms.length; i++ )
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                platforms[i][y].draw(canvas);
            }
        }
        frog.draw(canvas);
        health.draw(canvas);
        //gọi handler bảo nó update mỗi 0.01 giây
        handler.postDelayed(runnable,20 );
    }

}
