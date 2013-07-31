package com.RoboMobo;

import android.app.Activity;
import android.graphics.*;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 30.07.13
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class RMR
{
    public static Display display;
    public static int width;
    public static int height;
    public static Canvas c;
    public static Activity am;
    public static Random rnd;
    public static ArrayList<int[]> apples;

    public static Map currentMap;

    public static void init(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();
        apples = new ArrayList<int[]>();


        currentMap = new Map(10, 10, R.drawable.map_test);
    }

    /**
     * Update stuff.
     */
    public static void Update(long elapsedTime)
    {
        for(int[] apple : apples)
        {
            apple[2] -= elapsedTime;
            if(apple[2] <= 0) apples.remove(apple);
        }
    }


    /**
     * Draw stuff.
     */
    public static void Draw()
    {
        RMR.c.save();
        {
            int mapW = RMR.currentMap.width * 32;
            int mapH = RMR.currentMap.height * 32;
            RMR.c.translate(RMR.width / 2 - mapW, 0);



            Rect src = new Rect();
            Rect dst = new Rect();

            src.set(0, 0, RMGR.MAP_test.getWidth(), RMGR.MAP_test.getHeight());
            dst.set(0, 0, mapW * 2, mapH * 2);

            RMR.c.drawBitmap(RMGR.MAP_test, src, dst, new Paint());
        }
        RMR.c.restore();
    }

    public static void generateApple()
    {
        apples.add(new int[] {rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(20000)+10000});
    }
}
