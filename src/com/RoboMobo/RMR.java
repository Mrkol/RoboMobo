package com.RoboMobo;

import android.app.Activity;
import android.graphics.*;
import android.view.Display;

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
     * Side of the map in tiles.
     */
    public static int mapSide = 10;
    public static GPSModule gps;

    /**
     * Rotation/zoomDistance matrix.
     */
    public static Matrix transform;

    /**
     * Previous state of rotation/zoomDistance matrix.
     */
    public static Matrix prevTransform;

    /*
     * Control states.
     */
    public static  final short NONE = 0;
    public static  final short DRAG = 1;
    public static  final short ZOOM = 2;

    public static short transformMode = NONE;

    /**
     * Map zoomDistance.
     */
    public static float zoomDistance;

    /**
     * Map prevZoomDistance.
     */
    public static float prevZoomDistance;

    /**
     * Last point between the fingers (for zoom).
     */
    public static PointF midPoint;

    /**
     * The current map.
     */
    public static Map currentMap;

    public static void init(Activity act, GPSModule _gps)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();

        zoomDistance = 11;
        prevZoomDistance = 11;
        midPoint = new PointF();

        transform = new Matrix();
        prevTransform = new Matrix();

        currentMap = new Map(mapSide, mapSide, R.drawable.map_test);

        gps = _gps;
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
            RMR.c.setMatrix(RMR.transform);
            RMR.currentMap.Draw();
        }
        RMR.c.restore();
    }
}
