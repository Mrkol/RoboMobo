package com.RoboMobo;

import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 31.07.13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
public class Map
{
    /**
     * Resource ID of background for this map.
     */
    public final int background;
    public final int width;
    public final int height;
    public double corner1latt;
    public double corner1long;
    public double corner2latt;
    public double corner2long;
    public boolean corner1fixed;
    public boolean corner2fixed;
    public double basexlatt;
    public double basexlong;
    public double baseylatt;
    public double baseylong;
    public double def;
    public ArrayList<int[]> pickups;

    /**
     * Array of tile IDs. Every [width] indexes starts a new row.
     */
    public short[] tiles;

    public Map(int w, int h, int bgrid)
    {
        this.width = w;
        this.height = h;
        tiles = new short[width * height];
        this.background = bgrid;
        pickups = new ArrayList<int[]>();
        corner1fixed = false;
        corner2fixed = false;
    }

    public void Update(long elapsedTime)
    {
        for(int i = 0; i < this.pickups.size(); i++)
        {
            this.pickups.get(i)[2] -= elapsedTime;
            if(this.pickups.get(i)[2] <= 0) pickups.remove(this.pickups.get(i));
        }

        if(this.pickups.size() < 10 && RMR.rnd.nextInt(20) == 1)
        {
            this.generatePickups();
        }
    }

    public void Draw()
    {
        Paint pa = new Paint();
        Rect r0 = new Rect();
        Rect r1 = new Rect();

        r0.set(0, 0, RMGR.PICKUP_test.getWidth(), RMGR.PICKUP_test.getHeight());
        r1.set(0, 0, 32, 32);

        RMR.c.save();
        {
            for(int i = 0; i < this.pickups.size(); i++)
            {
                RMR.c.save();
                {
                    RMR.c.translate(this.pickups.get(i)[0] * 32, this.pickups.get(i)[1] * 32);
                    switch(this.pickups.get(i)[0])
                    {
                        default:
                            RMR.c.drawBitmap(RMGR.PICKUP_test, r0, r1, pa);
                            break;
                    }
                }
                RMR.c.restore();
            }
        }
        RMR.c.restore();
    }

    public void generatePickups()
    {
        pickups.add(new int[] {RMR.rnd.nextInt(RMR.cell), RMR.rnd.nextInt(RMR.cell), RMR.rnd.nextInt(20000)+10000, 0});
    }

    public void postInit()
    {

    }

    public void fixCorner1(double latt, double longt)
    {
        corner1latt = latt;
        corner1long = longt;
        corner1fixed = true;
    }

    public void fixCorner2(double latt, double longt)
    {
        corner2latt = latt;
        corner2long = longt;
        corner2fixed = true;
        double dbaseLatt = (corner2latt - corner1latt)/((corner2latt - corner1latt)*(corner2latt - corner1latt)+(corner2long - corner1long)*(corner2long - corner1long));
        double dbaseLong = (corner2long - corner1long)/((corner2latt - corner1latt)*(corner2latt - corner1latt)+(corner2long - corner1long)*(corner2long - corner1long));
        basexlatt = dbaseLatt/2 - dbaseLong/2;
        basexlong = dbaseLong/2 + dbaseLatt/2;
        baseylatt = dbaseLatt/2 + dbaseLong/2;
        baseylong = dbaseLong/2 - dbaseLatt/2;
        def = basexlatt*baseylong - basexlong*baseylatt;
    }

    public int[] coordTransform(double latt, double longt)
    {
        if (!(corner1fixed && corner2fixed))
            return null;
        int[] coord = new int[2];
        coord[1] = (int)((baseylong*latt/def-baseylatt*longt/def)*Math.sqrt((2048*RMR.cell*RMR.cell)/((corner2latt - corner1latt)*(corner2latt - corner1latt)+(corner2long - corner1long)*(corner2long - corner1long))));
        coord[2] = (int)((-basexlong*latt/def+basexlatt*longt/def)*Math.sqrt((2048*RMR.cell*RMR.cell)/((corner2latt - corner1latt)*(corner2latt - corner1latt)+(corner2long - corner1long)*(corner2long - corner1long))));
        return coord;
    }
}
