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

    public static Map currentMap;

    public static void init(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();

        currentMap = new Map(10, 10, R.drawable.map_test);
    }

    /**
     * Update stuff.
     */
    public static void Update(long elapsedTime)
    {
        RMR.currentMap.Update(elapsedTime);
    }


    /**
     * Draw stuff.
     */
    public static void Draw()
    {
        RMR.c.save();
        {
            RMR.c.scale(2, 2);
            int mapW = RMR.currentMap.width * 32;
            int mapH = RMR.currentMap.height * 32;
            RMR.c.translate(RMR.width / 4 - mapW / 2, 0);

            Paint pa = new Paint();

            Rect src = new Rect();
            Rect dst = new Rect();

            src.set(0, 0, RMGR.MAP_test.getWidth(), RMGR.MAP_test.getHeight());
            dst.set(0, 0, mapW, mapH);

            RMR.c.drawBitmap(RMGR.MAP_test, src, dst, pa);

            pa.setColor(Color.BLACK);

            RMR.c.save();
            {

                for(int i = 0; i < RMR.currentMap.width; i++)
                {
                    for(int j = 0; j < RMR.currentMap.height; j++)
                    {
                        RMR.c.save();
                        {
                            RMR.c.translate(i * 32, j * 32);
                            RMR.c.drawLine(0, 0, 32, 0, pa);
                            RMR.c.drawLine(0, 0, 0, 32, pa);
                            RMR.c.drawLine(32, 32, 32, 0, pa);
                            RMR.c.drawLine(32, 32, 0, 32, pa);
                        }
                        RMR.c.restore();
                    }
                }
            }
            RMR.c.restore();

            RMR.currentMap.Draw();
        }
        RMR.c.restore();
    }
}
