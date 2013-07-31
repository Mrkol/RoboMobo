package com.RoboMobo;

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

    }

    public void postInit()
    {

    }
}
