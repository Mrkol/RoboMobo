package com.RoboMobo;

import android.app.Activity;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    public static void init(Activity act)
    {
        display = act.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        am = act;
        rnd = new Random();

        playerID = "Asdf";

        uuid = UUID.fromString("0b344d07-87a9-41cd-bc61-0e4e22f15f4d");
    }

    public static void onServerConnected()
    {
        currentMap = new Map(mapSideLength, mapSideLength, R.drawable.map_test);

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
            jobj.put("Background", RMR.currentMap.background);
        }
        catch (JSONException e)
        {

        }


        try
        {
            RMR.btSocket.getOutputStream().write(jobj.toString().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void onClientConnected() //called after server sent map and statistics
    {

    }

    /**
     * Update stuff.
     */
    public static void Update(long elapsedTime)
    {
        RMR.currentMap.Update(elapsedTime);
    }


    /**
     * Draw stuff.
     */
    public static void Draw()
    {
        RMR.c.save();
        {
            Paint p = new Paint();
            p.setColor(Color.rgb(0x0, 0x0, 0xF));
            RMR.c.drawPaint(p);

            RMR.currentMap.Draw();
        }
        RMR.c.restore();
    }

    public enum GameState
    {
        Invalid,
        NotInGame,
        Client,
        Server
    }
}
