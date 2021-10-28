package com.example.froggame;

import android.os.Handler;
import android.util.Log;

import java.util.Random;

public class GameEvent {

    Frog frog;
    Platform[][] platforms;

    Random random;

    int num1,num2,eventNum;
    int MaxFly = 1;
    int CurrentFly = 0;

    public GameEvent()
    {
        random = new Random();

    }

    // trả về ngẫy nhiên platfrom
    public Platform getRandomPlatform()
    {
        while(true)
        {
            num1 = random.nextInt(4);
            num2 = random.nextInt(4);
            if(platforms[num1][num2] == frog.currentplatform)
            {
                continue;
            }
            if(platforms[num1][num2].HadEvent)
            {
                continue;
            }
            return platforms[num1][num2];
        }
    }

    public void StartRamdomEvent()
    {
        if(getHadEvent() != 16)
        {
            Platform platform = getRandomPlatform();
            if(CurrentFly < MaxFly)
            {
                platform.StartFlyEvent();
                CurrentFly++;
                return;
            }
            eventNum = random.nextInt(3);
            switch (eventNum)
            {
                case 0:
                {
                    platform.StartLilyEvent();
                    break;
                }
                case 1:
                {
                    platform.StartRockEvent();
                    break;
                }
                case 2:
                {
                    platform.StartCoinEvent();
                    break;
                }
            }
        }
    }

    public int getHadEvent()
    {
        int count = 0;
        for (int i = 0 ; i < platforms.length; i++)
        {
            for (int y = 0 ; y < platforms[0].length ; y++)
            {
                if( platforms[i][y].HadEvent)
                {
                    count++;
                }
            }
        }
        return count;
    }
}
