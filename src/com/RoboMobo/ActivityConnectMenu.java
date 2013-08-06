package com.RoboMobo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: loredan
 * Date: 06.08.13
 * Time: 12:08
 */
public class ActivityConnectMenu extends Activity
{
    public BluetoothAdapter btAdapter;
    public ArrayList<BluetoothDevice> devices;
    public ArrayAdapter<String> btArrAdapter;
    public static final int REQUEST_ENABLE_BT = 1;
    public LinearLayout llDevices;
    public ListView lvDevices;
    public BroadcastReceiver btDeviceFound;
    public ExpectConnectThread expectConnectThread;
    public Handler handler;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        llDevices = (LinearLayout) findViewById(R.id.ll_devices);
        lvDevices = (ListView) findViewById(R.id.lv_devices);
        btArrAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1);
        lvDevices.setAdapter(btArrAdapter);

        lvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Log.i("Item", "Click");
                String name = (String) ((TextView) view).getText();
                BluetoothDevice selectedDevice = null;
                BluetoothSocket temp = null;
                for (BluetoothDevice device : devices)
                {
                    String devName = device.getName();
                    if (device.getName().equals(name))
                    {
                        selectedDevice = device;
                        break;
                    }
                }
//                btAdapter.cancelDiscovery();
                try
                {
                    temp = selectedDevice.createRfcommSocketToServiceRecord(RMR.uuid);
                } catch (IOException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                RMR.btSocket = temp;
                try
                {
                    RMR.btSocket.connect();
                } catch (IOException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Intent connectIntent = new Intent(getApplicationContext(), ActivityMain.class);
                startActivity(connectIntent);
            }
        });

        Log.i("Bluetooth", "Fetch adapter");
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null)      //проверяем, есть ли bluetooth на телефоне
        {
            Toast.makeText(this, "Bluetooth is not available.", Toast.LENGTH_SHORT).show();
        }

        if (!btAdapter.isEnabled())
        {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_ENABLE_BT);
        }

        if (btAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) //проверяем, включена ли обнаружаемость другими устройствами
        {
            Intent discoverDevice = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); //bluetooth не включен
            discoverDevice.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(discoverDevice);                                                    //по пользовательскому соглашению включаем его
        } else
        {
            Toast.makeText(this, "Bluetooth is currently working", Toast.LENGTH_SHORT).show(); //говорим, что bluetooth уже работает
        }

        while (!btAdapter.isEnabled());
        Log.i("Bluetooth", "Adapter locked, start server connection thread");
        expectConnectThread = new ExpectConnectThread(btAdapter, this);
        expectConnectThread.start();
        Log.i("Bluetooth", "Thread started, device search");
        devices = new ArrayList<BluetoothDevice>(btAdapter.getBondedDevices());
        if (devices.size() > 0)
        {
            for (BluetoothDevice device : devices)
            {
//                devices.add(device);
                btArrAdapter.add(device.getName());
//                lvDevices.addView(tvDevice);
//                lvDevices.addView(tvDevice);
            }
        }

        btDeviceFound = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice foundDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.d("foundDevice", foundDevice == null ? "null" : "OK");
//                    Ref.devices.add(foundDevice);
                    TextView tvDevice = new TextView(getApplicationContext());
                    tvDevice.setText((CharSequence) foundDevice.getName());
                    devices.add(foundDevice);
                    btArrAdapter.add(foundDevice.getName());
//                    lvDevices.addView(tvDevice);
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btDeviceFound, filter);

//        btAdapter.startDiscovery();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(btDeviceFound);
    }
}
