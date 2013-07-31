package com.RoboMobo;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMain extends Activity
{
    /**
     * Called when the activity is first created.
     */
    public boolean flag = true;
    public GPSModule mlocListener;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        RMGR.init(this);
        RMR.init(this);
        setContentView(R.layout.main);
        Log.wtf("1", "1");
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new GPSModule();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }
    public void fixCoord(View v)
    {
       TextView fixCoord1 = (TextView) findViewById(R.id.tv_fix1);
       TextView fixCoord2 = (TextView) findViewById(R.id.tv_fix2);
       if (flag)
       {
            fixCoord1.setText("Первый угол: " + mlocListener.last_latt + ", "+mlocListener.last_long);
            flag = false;
       }
        else
       {
           fixCoord2.setText("Второй угол: " + mlocListener.last_latt + ", "+mlocListener.last_long);
           flag = true;
       }

    }
}
