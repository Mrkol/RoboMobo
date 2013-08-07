package com.RoboMobo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 31.07.13
 * Time: 11:01
 */
public class Map
{
    /**
     * Resource ID of background for this map.
     */
    public final int width;
    public final int height;
    public double corner1latt = 0;
    public double corner1long = 0;
    public double corner2latt = 0;
    public double corner2long = 0;
    public boolean corner1fixed;
    public boolean corner2fixed;
    public double basexlatt = 0;
    public double basexlong = 0;
    public double baseylatt = 0;
    public double baseylong = 0;
    public double det;
    public ArrayList<int[]> pickups;
    public Player p0;
    public Player p1;

    public float prevFilteredCompass = 0;

    public MapState state;

    public Point suspendTile;


    /**
     * Array of tile IDs. Every [width] indexes starts a new row.
     */
    public short[][] tiles;

    public Map(int w, int h)
    {
        this.width = w;
        this.height = h;
        pickups = new ArrayList<int[]>();
        corner1fixed = false;
        corner2fixed = false;

        tiles = new short[RMR.mapSideLength][RMR.mapSideLength];

        for (int i = 0; i < RMR.mapSideLength; i++)
        {
            for (int j = 0; j < RMR.mapSideLength; j++)
            {
                tiles[i][j] = 0;
            }
        }

        for (int i = 0; i < 3; i++)
        {
            tiles[3][i] = 1;
        }
        for (int i = 2; i < 7; i++)
        {
            tiles[i][5] = 1;
        }

        state = MapState.PreGame;
        this.suspendTile = null;

        /*Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                Generation.generateRivers(tiles, width, height);
            }
        };
        r.run();*/
    }

    public void Start()
    {
        state = MapState.Game;
    }

    public void Update(long elapsedTime)
    {
        //Log.wtf("current coords", RMR.gps.last_latt + " " + RMR.gps.last_long);
        int[] coord = coordTransform(RMR.gps.last_latt, RMR.gps.last_long);
        if (coord != null)
        {
            this.p0.changePos(coord);
        }

        if (this.suspendTile != null && (Math.floor(this.p0.posX / 32.0) == this.suspendTile.x && Math.floor(this.p0.posY / 32.0) == this.suspendTile.y))
        {
            this.suspendTile = null;
            this.state = MapState.Game;
        }

        JSONObject pl = new JSONObject();
        try
        {
            pl.put("X", p0.posX);
            pl.put("Y", p0.posY);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        JSONObject jobj = new JSONObject();
        try
        {
            jobj.put("Player", pl);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (RMR.state == RMR.GameState.ClientIngame || RMR.state == RMR.GameState.ServerIngame)
        {
            ((ActivityMain) RMR.am).dataExchange.write(jobj);
        }

        if (state == MapState.Game)
        {
            if (this.p0.posX < 0 || this.p0.posY < 0 || Math.floor(this.p0.posX / 32.0) >= RMR.mapSideLength || Math.floor(this.p0.posY / 32.0) >= RMR.mapSideLength)
            {
                this.state = MapState.Suspended;
                this.suspendTile = new Point();
                this.suspendTile.set((int) Math.floor(this.p0.prevPosX / 32.0), (int) Math.floor(this.p0.prevPosY / 32.0));
            }
            else if (this.tiles[((int) Math.floor(this.p0.posX / 32.0))][((int) Math.floor(this.p0.posY / 32.0))] != 0)
            {
                this.state = MapState.Suspended;
                this.suspendTile = new Point();
                this.suspendTile.set((int) Math.floor(this.p0.prevPosX / 32.0), (int) Math.floor(this.p0.prevPosY / 32.0));
            }

            for (int i = 0; i < this.pickups.size(); i++)
            {
                this.pickups.get(i)[2] -= elapsedTime;
                if (this.pickups.get(i)[2] <= 0)
                {
                    pickups.remove(this.pickups.get(i));
                }
            }

            if (this.pickups.size() < 10 && RMR.rnd.nextInt(20) == 1)
            {
                int x = RMR.rnd.nextInt(RMR.mapSideLength);
                int y = RMR.rnd.nextInt(RMR.mapSideLength);
                if (this.tiles[x][y] == 0)
                {
                    pickups.add(new int[]{x, y, RMR.rnd.nextInt(20000) + 10000, 1});
                }
            }

            for (int i = 0; i < pickups.size(); i++)
            {
                if ((Math.floor(this.p0.posX / 32.0) == this.pickups.get(i)[0]) && (Math.floor(this.p0.posY / 32.0) == this.pickups.get(i)[1]))
                {
                   /*Log.wtf("Pl", Math.floor(this.player1.posX / 32) + " " + Math.floor(this.player1.posY / 32));
                   Log.wtf("Pick", this.pickups.get(i)[0] + " " + this.pickups.get(i)[1]);*/
                    this.p0.addScore(1);
                    this.pickups.remove(i);
                    Message msg = new Message();
                    msg.arg1 = this.p0.score;
                    ((ActivityMain) RMR.am).HandlerUIUpdate.sendMessage(msg);
                }
            }
            /*TextView text = (TextView) RMR.am.findViewById(R.id.tv_score);
            text.setText("Очки: "+this.player1.score);*/
        }
    }

    public void Draw()
    {
        Paint pa = new Paint();
        RMR.c.save();
        {
            int mapW = this.width * 32;
            int mapH = this.height * 32;

            Player p = this.p0;

            double mapRotation = Math.toDegrees(Math.asin(Math.abs(this.basexlong - this.baseylong) / Math.sqrt(Math.pow(this.basexlatt - this.baseylatt, 2) + Math.pow(this.basexlong - this.baseylong, 2))));
            //double playerAngle = Math.toDegrees(Math.asin(Math.abs(p.posY - p.prevPosY) / Math.sqrt(Math.pow(p.posX - p.prevPosX, 2) + Math.pow(p.posY - p.prevPosY, 2))));
            if ((p.posX - p.prevPosX) >= 0)
            {
                if ((p.posY - p.prevPosY) >= 0)
                {
                    mapRotation = 180 - mapRotation;
                }
                else
                {
                    mapRotation = 180 + mapRotation;
                }
            }
            else
            {
                if ((p.posY - p.prevPosY) < 0)
                {
                    mapRotation = 360 - mapRotation;
                }
            }

            RMR.c.scale(((float) RMR.sw.getHeight() / (float) mapH), ((float) RMR.sw.getHeight() / (float) mapH));


            Rect src = new Rect();
            Rect dst = new Rect();

            float α = 0.9f;

            RMR.c.save();
            {
                RMR.c.translate(RMR.mapSideLength * 32 / 2, RMR.mapSideLength * 32 / 2);
                /*if(this.corner1fixed && this.corner2fixed)*/
                double delta = prevFilteredCompass + Math.toDegrees(RMR.compass.orientationData[0]);
                delta = Math.abs(delta)>180 ? delta - 360 : delta;
                prevFilteredCompass = (float) (α * delta - Math.toDegrees(RMR.compass.orientationData[0]));
//                prevFilteredCompass = (α * prevFilteredCompass) + ((1 - α) * ((float) Math.toDegrees(-RMR.compass.orientationData[0])));
                if (this.corner1fixed && this.corner2fixed)
                {
                    RMR.c.rotate((!Double.isNaN(mapRotation) ? (float) mapRotation : 0) - prevFilteredCompass/*-(float) playerAngle*/, 0, 0);
                }
                RMR.c.translate(-this.p0.posY, -this.p0.posX);

                src.set(0, 0, RMGR.MAP_test.getWidth(), RMGR.MAP_test.getHeight());
                dst.set(0, 0, mapW, mapH);

                RMR.c.drawBitmap(RMGR.MAP_test, src, dst, pa);

                pa.setColor(Color.BLACK);

                RMR.c.save();
                {

                    for (int i = 0; i < RMR.currentMap.width; i++)
                    {
                        for (int j = 0; j < RMR.currentMap.height; j++)
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


                RMR.c.save();
                {
                    for (int i = 0; i < this.height; i++)
                    {
                        for (int j = 0; j < this.width; j++)
                        {
                            if (this.tiles[j][i] == 0)
                            {
                                continue;
                            }
                            RMR.c.save();
                            {
                                RMR.c.translate(i * 32, j * 32);
                                src.set(0, 0, RMGR.TILE_test.getWidth(), RMGR.TILE_test.getHeight());
                                dst.set(0, 0, 32, 32);
                                pa.setColor(Color.WHITE);
                                RMR.c.drawBitmap(RMGR.TILE_test, src, dst, pa);
                            }
                            RMR.c.restore();
                        }
                    }
                }
                RMR.c.restore();


                src.set(0, 0, RMGR.PICKUP_test.getWidth(), RMGR.PICKUP_test.getHeight());
                dst.set(0, 0, 32, 32);

                RMR.c.save();
                {
                    for (int i = 0; i < this.pickups.size(); i++)
                    {
                        RMR.c.save();
                        {
                            RMR.c.translate(this.pickups.get(i)[1] * 32, this.pickups.get(i)[0] * 32);
                            switch (this.pickups.get(i)[0])
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

                if (this.state == MapState.Suspended)
                {
                    RMR.c.save();
                    {
                        pa.setColor(Color.BLACK);
                        pa.setAlpha(80);

                        RMR.c.drawRect(0, 0, RMR.mapSideLength * 32, this.suspendTile.x * 32, pa);
                        RMR.c.drawRect((this.suspendTile.y + 1) * 32, this.suspendTile.x * 32, RMR.mapSideLength * 32, (this.suspendTile.x + 1) * 32, pa);
                        RMR.c.drawRect(0, this.suspendTile.x * 32, this.suspendTile.y * 32, (this.suspendTile.x + 1) * 32, pa);
                        RMR.c.drawRect(0, (this.suspendTile.x + 1) * 32, RMR.mapSideLength * 32, RMR.mapSideLength * 32, pa);

                        pa.setColor(Color.YELLOW);
                        pa.setAlpha((int) Math.floor((Math.sin(System.currentTimeMillis() / 100) + 2) * 30));
                        RMR.c.drawRect(this.suspendTile.y * 32, this.suspendTile.x * 32, (this.suspendTile.y + 1) * 32, (this.suspendTile.x + 1) * 32, pa);
                    }
                    RMR.c.restore();
                }
                RMR.c.save();
                {
                    pa.setColor(Color.MAGENTA);
                    RMR.c.translate(this.p0.prevPosY, this.p0.prevPosX);
                    RMR.c.drawRect(-4, -4, 4, 4, pa);
                }
                RMR.c.restore();
            }
            RMR.c.restore();


            RMR.c.save();
            {
                pa.setColor(Color.WHITE);
                RMR.c.translate(RMR.mapSideLength * 32 / 2, RMR.mapSideLength * 32 / 2);
                src.set(0, 0, RMGR.CHAR_test.getWidth(), RMGR.CHAR_test.getHeight());
                dst.set(-8, -8, 8, 8);
                RMR.c.drawBitmap(RMGR.CHAR_test, src, dst, pa);
            }
            RMR.c.restore();
        }
        RMR.c.restore();
    }

    public void fixCorner1(double latt, double longt)
    {
        if (corner1fixed)
        {
            return;
        }
        corner1latt = latt;
        corner1long = longt;
        corner1fixed = true;
    }

    public void fixCorner2(double latt, double longt)
    {
        if (corner2fixed)
        {
            return;
        }
        corner2latt = latt;
        corner2long = longt;
        corner2fixed = true;
        double dbaseLatt = (corner2latt - corner1latt) / Math.sqrt((corner2latt - corner1latt) * (corner2latt - corner1latt) + (corner2long - corner1long) * (corner2long - corner1long));
        double dbaseLong = (corner2long - corner1long) / Math.sqrt((corner2latt - corner1latt) * (corner2latt - corner1latt) + (corner2long - corner1long) * (corner2long - corner1long));
        basexlatt = (dbaseLatt / 2 - dbaseLong / 2) * Math.sqrt(2);
        basexlong = (dbaseLong / 2 + dbaseLatt / 2) * Math.sqrt(2);
        baseylatt = (dbaseLatt / 2 + dbaseLong / 2) * Math.sqrt(2);
        baseylong = (dbaseLong / 2 - dbaseLatt / 2) * Math.sqrt(2);
        det = basexlatt * baseylong - basexlong * baseylatt;
        this.p0.posX = 32 * (RMR.mapSideLength - 1);
        this.p0.posY = 32 * (RMR.mapSideLength - 1);
        //Log.wtf("dbase",Double.toString(Math.sqrt(dbaseLatt*dbaseLatt+dbaseLong*dbaseLong)));
        //Log.wtf("basex",Double.toString(Math.sqrt(basexlatt*basexlatt+baseylong*baseylong)));
        //Log.wtf("basey",Double.toString(Math.sqrt(baseylatt*baseylatt+baseylong*baseylong)));
    }

    public int[] coordTransform(double latt, double longt)
    {
        if (!(corner1fixed && corner2fixed))
        {
            return null;
        }
        double relLatt = latt - corner1latt;
        double relLong = longt - corner1long;
        int[] coord = new int[2];
        coord[0] = (int) ((baseylong * relLatt / det - baseylatt * relLong / det) * Math.sqrt((2048 * RMR.mapSideLength * RMR.mapSideLength) / ((corner2latt - corner1latt) * (corner2latt - corner1latt) + (corner2long - corner1long) * (corner2long - corner1long))));
        coord[1] = (int) ((-basexlong * relLatt / det + basexlatt * relLong / det) * Math.sqrt((2048 * RMR.mapSideLength * RMR.mapSideLength) / ((corner2latt - corner1latt) * (corner2latt - corner1latt) + (corner2long - corner1long) * (corner2long - corner1long))));
        return coord;
    }

    public enum MapState
    {
        Invalid,
        PreGame,
        Game,
        Suspended,
        PostGame
    }
}
