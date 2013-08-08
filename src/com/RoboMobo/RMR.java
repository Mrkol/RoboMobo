package com.RoboMobo;

import android.app.Activity;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 30.07.13
 * Time: 12:50
 */
public class RMR
{
    /**
     * Display object.
     */
    public static Display display;

    /**
     * Width of display.
     */
    public static int width;

    /**
     * Height of display.
     */
    public static int height;

    /**
     * Canvas. Used to draw things on screen.
     */
    public static Canvas c;

    /**
     * Main ingame activity.
     */
    public static Activity am;

    /**
     * Random generator.
     */
    public static Random rnd;
    public static GPSModule gps;
    public static CompassModule compass;

    public static MainSurfaceView sw;

    public static UUID uuid;
    public static BluetoothServerSocket btServerSocket;
    public static BluetoothSocket btSocket;
    public static final int INCOMING_MESSAGE = 1;

    public static String playerID;

    public static GameState state = GameState.NotInGame;

    /**
     * The current map.
     */
    public static Map currentMap;
    public static int mapSideLength = 10;

    public static void init()
    {

        rnd = new Random();

        playerID = "Asdf";

        uuid = UUID.fromString("0b344d07-87a9-41cd-bc61-0e4e22f15f4d");
    }

    public static void registerActivity(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;

        if(RMR.state == GameState.Server)
        {
            RMR.onServerConnected();
        }

        if(RMR.state == GameState.Singleplayer) RMR.state = GameState.SingleplayerIngame;
    }

    public static void onServerConnected()
    {
        Log.i("Server", "Generating map");
        currentMap = new Map(mapSideLength, mapSideLength);

        /*
            Generate da map
         */
        RMR.currentMap.p0 = new Player(0, 0, "P0", true);

        JSONArray jarr = new JSONArray();

        for(int i = 0; i < RMR.currentMap.width; i++)
        {
            JSONArray jarrr = new JSONArray();
            for(int j = 0; j < RMR.currentMap.height; j++)
            {
                jarrr.put(RMR.currentMap.tiles[i][j]);
            }
            jarr.put(jarrr);
        }

        JSONObject jobj = new JSONObject();

        try
        {
            jobj.put("Tiles", jarr);
        }
        catch (JSONException e)
        {

        }


        JSONObject jo = new JSONObject();
        try
        {
            jo.put("Map", jobj);
            jo.put("width", RMR.currentMap.width);
            jo.put("height", RMR.currentMap.height);
        }
        catch (JSONException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Log.i("Server", "Awaiting for connection");
        try
        {
            RMR.btSocket.getInputStream().read();
            ((ActivityMain)RMR.am).dataExchange.write(jo);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        RMR.state = GameState.ServerIngame;
        RMR.currentMap.Start();
    }

    public static void onClientConnected() //called after server sent map and statistics
    {

    }

    /**
     * Update stuff.
     */
    public static void Update(long elapsedTime)
    {
        if(RMR.state == GameState.NotInGame)
        {
            JSONObject jobj = new JSONObject();
            try
            {
                jobj.put("ready", true);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            JSONObject[] joar = Networking.get(jobj);
            try
            {
                for(int i = 0; i < joar.length; i++)
                {
                    JSONObject jo = joar[i];
                    if(jo.has("ready") && jo.getBoolean("ready"))
                    {
                        if(Networking.isServer)
                        {
                            RMR.state = RMR.GameState.ServerIngame;
                        }
                        else
                        {
                            RMR.state = RMR.GameState.ClientIngame;
                        }
                        break;
                    }
                    else
                    {
                        Thread.sleep(5000);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }


        if(RMR.state == GameState.ClientIngame || RMR.state == GameState.ServerIngame || RMR.state == GameState.SingleplayerIngame) RMR.currentMap.Update(elapsedTime);
    }


    /**
     * Draw stuff.
     */
    public static void Draw()
    {
        RMR.c.save();
        {
            RMGR.animationTimer++;
            if(RMGR.animationTimer == 3) RMGR.animationTimer = 0;
            Paint p = new Paint();
            p.setColor(Color.rgb(0x20, 0x20, 0x20));
            RMR.c.drawPaint(p);

            if(RMR.state == GameState.ClientIngame || RMR.state == GameState.ServerIngame || RMR.state == GameState.SingleplayerIngame) RMR.currentMap.Draw();
        }
        RMR.c.restore();
    }

    public enum GameState
    {
        Invalid,
        NotInGame,
        Client,
        Server,
        ClientIngame,
        ServerIngame,
        Singleplayer,
        SingleplayerIngame
    }
}
