package com.RoboMobo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

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

        pa = new Paint();

        src.set(0, 0, RMGR.PICKUP_test.getWidth(), RMGR.PICKUP_test.getHeight());
        dst.set(0, 0, 32, 32);

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
                            RMR.c.drawBitmap(RMGR.PICKUP_test, src, dst, pa);
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
        pickups.add(new int[] {RMR.rnd.nextInt(10), RMR.rnd.nextInt(10), RMR.rnd.nextInt(20000)+10000, 0});
    }

    public void postInit()
    {

    }
}
