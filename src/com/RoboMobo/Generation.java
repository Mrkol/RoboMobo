package com.RoboMobo;

import android.graphics.Point;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 05.08.13
 * Time: 16:
 */
public class Generation
{
    public static void generateRivers(short[][] tiles, int width, int height)
    {
        int[][] temp = new int[width][height];

        int i0 = RMR.rnd.nextInt(height);
        int j0 = 0;
        int i1 = RMR.rnd.nextInt(height);
        int j1 = 0;

        if(i0 == 0 || i0 == (height - 1))
        {
            j0 = RMR.rnd.nextInt(width);
        }
        else
        {
            j0 = RMR.rnd.nextInt(1) * width;
        }

        if(i1 == 0 || i1 == (height - 1))
        {
            j1 = RMR.rnd.nextInt(1) * width;
        }
        else
        {
            j1 = RMR.rnd.nextInt(width);
        }

        riverRecursion(tiles, i0, j0, i1, j1, width, height, 0);
    }

    private static void riverRecursion(short[][] tiles, int x0, int y0, int x1, int y1, int width, int height, int p)
    {
        tiles[Math.abs(x0)][Math.abs(y0)] = 1;
        if(Math.abs(x0) == x1 && Math.abs(y0) == y1) return;
        Point point = new Point(x0, y0);
        int result = 0;
        int[] ints = new int[4];
        int[] ints1 = new int[4];
        ints[0] = ints1[0] = Math.round((Math.abs(Math.abs(x0 + 1) - Math.abs(x1)) + Math.abs(Math.abs(y0) - Math.abs(y1))) / 2);
        ints[1] = ints1[1] = Math.round((Math.abs(Math.abs(x0 - 1) - Math.abs(x1)) + Math.abs(Math.abs(y0) - Math.abs(y1))) / 2);
        ints[2] = ints1[2] = Math.round((Math.abs(Math.abs(x0) - Math.abs(x1)) + Math.abs(Math.abs(y0 + 1) - Math.abs(y1))) / 2);
        ints[3] = ints1[3] = Math.round((Math.abs(Math.abs(x0) - Math.abs(x1)) + Math.abs(Math.abs(y0 - 1) - Math.abs(y1))) / 2);

        Arrays.sort(ints);

        int r = RMR.rnd.nextInt(9);

        if(r == 0) result = ints[0];
        if(r > 0 && r < 3) result = ints[1];
        if(r >= 3 && r < 7) result = ints[2];
        if(r >= 7) result = ints[3];

        int rs = Arrays.binarySearch(ints1, result);

        switch(rs)
        {
            case 0:
                point.set(x0 + 1, y0);
                break;

            case 1:
                point.set(x0 - 1, y0);
                break;

            case 2:
                point.set(x0, y0 + 1);
                break;

            case 3:
                point.set(x0, y0 - 1);
                break;

            default:
                point.set(x0, y0);
                break;
        }

        riverRecursion(tiles, point.x, point.y, x1, y1, width, height, p + 1);
    }
}
