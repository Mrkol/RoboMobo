package com.RoboMobo;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Nats
 * Date: 31.07.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class GPSModule implements LocationListener
{
    public double last_latt;
    public double last_long;

    @Override
    public void onLocationChanged(Location location)
    {
        last_latt = location.getLatitude();      // широта
        last_long = location.getLongitude();     // долгота
        TextView text = (TextView) RMR.am.findViewById(R.id.tv_coord);
        text.setText("Координаты: " + last_latt + ", " + last_long);
        //Log.wtf("1",Double.toString(last_latt) + ' ' + Double.toString(last_long));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {
    }

    @Override
    public void onProviderEnabled(String s)
    {
        Toast.makeText(RMR.am.getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s)
    {
        Toast.makeText(RMR.am.getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT).show();
    }
}
