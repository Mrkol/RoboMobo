package com.RoboMobo;

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

    public Player(int x, int y)
    {
        posX = x;
        posY = y;
        score = 0;
    }

    public void changePos(int[] coord)
    {
        if ((posX != coord[0]) || (posY != coord[1]))
        {
            prevPosX = posX;
            prevPosY = posY;
            posX = coord[0];
            posY = coord[1];
        }
        //Log.wtf("player","x: "+posX+", y: "+posY);
    }

    public void addPoint(int _score)
    {
        score += _score;
    }
}
