package com.RoboMobo;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Nats
 * Date: 31.07.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class Player
{
    int posX;
    int posY;
    int point;

    public Player(int x, int y, int p)
    {
        posX = x;
        posY = y;
        point =p;
    }
    public void changePos(int[] coord)
    {
        posX = coord[0];
        posY = coord[1];
        //Log.wtf("player","x: "+posX+", y: "+posY);
    }
    public void addPoint(int _point)
    {
        point+=_point;
    }
}
