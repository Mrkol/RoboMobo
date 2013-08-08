package com.RoboMobo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 31.07.13
 * Time: 12:51
 */
public class RMGR
{
    public static Bitmap PICKUP_0;
    public static Bitmap CHAR_0;
    public static Bitmap[] TILE_0;
    public static short tile_0_iterator = 0;
    public static short animationTimer = 0;

    public static void init(ActivityMain activityMain)
    {
        TILE_0 = new Bitmap[5];
        PICKUP_0 = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.pickup_0);
        CHAR_0 = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.char_test);
        TILE_0[0] = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_0_0);
        TILE_0[1] = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_0_1);
        TILE_0[2] = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_0_2);
        TILE_0[3] = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_0_3);
        TILE_0[4] = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_0_4);
    }
}
