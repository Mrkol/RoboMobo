package com.RoboMobo;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: loredan
 * Date: 05.08.13
 * Time: 16:24
 */
public class ExpectConnectThread extends Thread
{
    ActivityConnectMenu activity;
    BluetoothAdapter adapter;


    public ExpectConnectThread(BluetoothAdapter btAdapter, ActivityConnectMenu _activity)
    {
        Log.i("ExpectConnectThread", "started");
        adapter = btAdapter;
        activity = _activity;
        try
        {
            RMR.btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord("BTTest2", RMR.uuid);
        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            Log.i("Bluetooth", "Try");
            try
            {
                RMR.btSocket = RMR.btServerSocket.accept();
            } catch (IOException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            Log.i("Bluetooth", "Fail");
            if(RMR.btSocket!=null)
            {
                Log.i("Bluetooth", "Connection detected");
                try
                {
                    RMR.btServerSocket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
//                adapter.cancelDiscovery();
                Intent connectIntent = new Intent(activity, ActivityMain.class);
                activity.startActivity(connectIntent);
                break;
            }
        }
    }
}
