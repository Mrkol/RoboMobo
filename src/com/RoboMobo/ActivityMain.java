package com.RoboMobo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ActivityMain extends Activity// implements View.OnTouchListener
{
    public boolean flag = true;
    public SensorManager msensorManager;

    public final Handler HandlerUIUpdate = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            TextView tv = (TextView) RMR.am.findViewById(R.id.tv_score);
            tv.setText("Очки: " + msg.arg1);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RMGR.init(this);
        setContentView(R.layout.main);
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        RMR.gps = new GPSModule();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, RMR.gps);
        //((MainSurfaceView) findViewById(R.id.view)).setOnTouchListener(this);
        RMR.compass = new CompassModule();
        msensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        RMR.sw = (MainSurfaceView) findViewById(R.id.view_ingame_canvas);
        RMR.init(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        msensorManager.registerListener(RMR.compass, msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        msensorManager.registerListener(RMR.compass, msensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        msensorManager.unregisterListener(RMR.compass);
    }

    public void fixCoord(View view)
    {
        if (!RMR.suspended)
        {
            if (flag)
            {
                RMR.currentMap.fixCorner1(RMR.gps.last_latt, RMR.gps.last_long);
                flag = false;
                Log.wtf("fix", "1");
            }
            else
            {
                RMR.currentMap.fixCorner2(RMR.gps.last_latt, RMR.gps.last_long);
                flag = true;
                Log.wtf("fix", "2");
            }
        }
    }

    public void moveUp(View view)
    {
        RMR.currentMap.player1.prevPosX = RMR.currentMap.player1.posX;
        RMR.currentMap.player1.prevPosY = RMR.currentMap.player1.posY;
        RMR.currentMap.player1.posX -= 32;
    }

    public void moveDown(View view)
    {
        RMR.currentMap.player1.prevPosX = RMR.currentMap.player1.posX;
        RMR.currentMap.player1.prevPosY = RMR.currentMap.player1.posY;
        RMR.currentMap.player1.posX += 32;
    }

    public void moveRight(View view)
    {
        RMR.currentMap.player1.prevPosX = RMR.currentMap.player1.posX;
        RMR.currentMap.player1.prevPosY = RMR.currentMap.player1.posY;
        RMR.currentMap.player1.posY += 32;
    }

    public void moveLeft(View view)
    {
        RMR.currentMap.player1.prevPosX = RMR.currentMap.player1.posX;
        RMR.currentMap.player1.prevPosY = RMR.currentMap.player1.posY;
        RMR.currentMap.player1.posY -= 32;
    }

    public void setPlayer(View view)
    {
        if (RMR.currentMap.player1.posX != 16 && RMR.currentMap.player1.posY != 16)
        {
            RMR.currentMap.player1.changePos(new int[]{16, 16});

            RMR.suspendTile = new Point(0, 0);
            RMR.suspended = false;
        }
    }

    public void rotateUp(View view)
    {
        RMR.currentMap.player1.prevPosX--;
    }

    public void rotateDown(View view)
    {
        RMR.currentMap.player1.prevPosX++;
    }

    public void rotateLeft(View view)
    {
        RMR.currentMap.player1.prevPosY--;
    }

    public void rotateRight(View view)
    {
        RMR.currentMap.player1.prevPosY++;
    }
}
