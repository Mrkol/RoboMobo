package com.RoboMobo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;

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
    public static List<int[]> apples;

    public static void init(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();
    }
}
