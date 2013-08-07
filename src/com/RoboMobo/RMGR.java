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
    public static Bitmap TILE_0;

    public static void init(ActivityMain activityMain)
    {
        PICKUP_0 = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.pickup_0);
        CHAR_0 = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.char_test);
        TILE_0 = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.tile_1);
    }
}
