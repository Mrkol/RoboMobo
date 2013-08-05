package com.RoboMobo;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 30.07.13
 * Time: 12:50
 */
public class RMR
{
    /**
     * Display object.
     */
    public static Display display;

    /**
     * Width of display.
     */
    public static int width;

    /**
     * Height of display.
     */
    public static int height;

    /**
     * Canvas. Used to draw things on screen.
     */
    public static Canvas c;

    /**
     * Main ingame activity.
     */
    public static Activity am;

    /**
     * Random generator.
     */
    public static Random rnd;
    /**
     * Side of the map in labyrinth.
     */
    public static int mapSide = 10;
    public static GPSModule gps;

    public static MainSurfaceView sw;

    /**
     * The current map.
     */
    public static Map currentMap;

    public static boolean suspended;

    public static Point suspendTile;

    public static void init(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();

        suspended = false;
        suspendTile = new Point();

        currentMap = new Map(mapSide, mapSide, R.drawable.map_test);
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
            Paint p = new Paint();
            p.setColor(Color.rgb(0x40, 0xF, 0xF));
            RMR.c.drawPaint(p);

            RMR.currentMap.Draw();
        }
        RMR.c.restore();
    }
}
