package com.RoboMobo;

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
public class gpsModule implements LocationListener
{
    public Context context;
    public double last_latt;
    public double last_long;

    public gpsModule(Context _context)
    {
        context = _context;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        //TextView text = (TextView) findViewById(R.id.tv1);
        last_latt = location.getLatitude();      // широта
        last_long = location.getLongitude();     // долгота
        //text.setText("Координаты: " + last_latt + ", " + last_long);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {
    }

    @Override
    public void onProviderEnabled(String s)
    {
        Toast.makeText(context, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s)
    {
        Toast.makeText(context, "GPS Disabled", Toast.LENGTH_SHORT).show();
    }
}
