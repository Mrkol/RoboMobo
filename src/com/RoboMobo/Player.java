package com.RoboMobo;

import android.bluetooth.BluetoothSocket;

/**
 * Created with IntelliJ IDEA.
 * User: Nats
 * Date: 31.07.13
 * Time: 16:54
 */
public class Player
{
    public int posX;
    public int posY;
    public int prevPosX;
    public int prevPosY;
    public int score;
    public boolean isLocal;
    public String id;

    public Player(int x, int y, String _id, boolean _isLocal)
    {
        posX = x;
        posY = y;
        score = 0;
        this.isLocal = _isLocal;
        this.id = _id;
    }

    public void changePos(int[] coord)
    {
        if ((posX != coord[0]) || (posY != coord[1]) && this.isLocal)
        {
            prevPosX = posX;
            prevPosY = posY;
            posX = coord[0];
            posY = coord[1];
        }
        //Log.wtf("player","x: "+posX+", y: "+posY);
    }

    public void addScore(int _score)
    {
        score += _score;
    }
}
