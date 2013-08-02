package com.RoboMobo;

/**
 * Created with IntelliJ IDEA.
 * User: loredan
 * Date: 01.08.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class Labyrinth
{
    public short[][] tiles;

    public Labyrinth()
    {
        tiles = new short[RMR.mapSide][RMR.mapSide];

        for (int i = 0; i < RMR.mapSide; i++)
        {
            for (int j = 0; j < RMR.mapSide; j++)
            {
                tiles[i][j] = 0;
            }
        }
        /*for (int i = 0; i < RMR.mapSide; i++)
        {
            for (int j = 0; j < RMR.mapSide; j++)
            {
                if (i == 0 || i == RMR.mapSide || j == 0 || j == RMR.mapSide)
                {
                    generateWall(i, j);
                }
            }
        }*/

        for (int i = 0; i < 3; i++)
        {
            tiles[3][i] = 1;
        }
        for (int i = 2; i < 7; i++)
        {
            tiles[i][5] = 1;
        }
    }

    private short checkAround(int x, int y)
    {
        short free = 0;
        if (x + 1 < RMR.mapSide)
        {
            if (tiles[x + 1][y] == 0)
            {
                free++;
            }
        }
        if (x - 1 >= 0)
        {
            if (tiles[x - 1][y] == 0)
            {
                free++;
            }
        }
        if (y + 1 < RMR.mapSide)
        {
            if (tiles[x][y + 1] == 0)
            {
                free++;
            }
        }
        if (y - 1 >= 0)
        {
            if (tiles[x][y - 1] == 0)
            {
                free++;
            }
        }
        if (x >= 0 && x + 1 < RMR.mapSide && y >= 0 && y + 1 < RMR.mapSide)
        {
            if (tiles[x][y] == 0)
            {
                free++;
            }
        }
        return free;
    }

    private void generateWall(int x, int y)
    {
        if (checkAround(x, y) == 3)
        {
            if (RMR.rnd.nextInt(10) < 8)
            {
                tiles[x][y] = 1;
                if (x + 1 < RMR.mapSide)
                {
                    if (tiles[x + 1][y] == 0)
                    {
                        generateWall(x + 1, y);
                    }
                }
                if (x - 1 >= 0)
                {
                    if (tiles[x - 1][y] == 0)
                    {
                        generateWall(x - 1, y);
                    }
                }
                if (y + 1 < RMR.mapSide)
                {
                    if (tiles[x][y + 1] == 0)
                    {
                        generateWall(x, y + 1);
                    }
                }
                if (y - 1 >= 0)
                {
                    if (tiles[x][y - 1] == 0)
                    {
                        generateWall(x, y - 1);
                    }
                }
            }
        }
    }
}
