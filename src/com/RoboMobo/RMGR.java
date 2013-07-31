package com.RoboMobo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 31.07.13
 * Time: 12:51
 * To change this template use File | Settings | File Templates.
 */
public class RMGR
{
    public static Bitmap MAP_test;
    public static Bitmap PICKUP_test;

    public static void init(ActivityMain activityMain)
    {
        MAP_test = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.map_test);
        PICKUP_test = BitmapFactory.decodeResource(activityMain.getResources(), R.drawable.pickup_test);
    }
}
