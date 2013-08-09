package com.RoboMobo;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: loredan
 * Date: 03.08.13
 * Time: 10:05
 */
public class Networking extends AsyncTask<JSONObject, Void, JSONObject[]>
{
    public HttpURLConnection connection;
    public URL url;
    public String msg;
    public String ip;
    public InputStream in;
    public OutputStream out;
    public boolean isServer;
    public Networking(String _ip, boolean server)
    {
        ip = _ip;
        msg = "http://";
        msg += ip;
        msg += server ? ":8192/TOP1?" : ":8192/TOP0?";
//        try
//        {
//            connection = (HttpURLConnection) url.openConnection();
//        }
//        catch (MalformedURLException e)
//        {
//            Log.wtf("URL", e.getMessage());
//        }
//        catch (IOException e)
//        {
//            Log.wtf("IO", e.getMessage());
//        }
        isServer = server;
    }

    @Override
    protected JSONObject[] doInBackground(JSONObject... send)
    {
        JSONObject[] received;
        try
        {
            connection = (HttpURLConnection) (new URL(msg + send[0].toString())).openConnection();
            Log.wtf("Sent", send[0].toString());
            InputStream is = connection.getInputStream();
//            OutputStream os = connection.getOutputStream();
//            os.write(send.toString().getBytes());
            int j = 0;
            while (is.available()==0)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                j++;
                if(j>=20)
                    return null;
            }
            byte[] raw = new byte[is.available()];
            is.read(raw);
            String[] str = URLDecoder.decode(new String(raw)).split("&");

//            String[] str = URLDecoder.decode(connection.getResponseMessage()).split("&");
            received = new JSONObject[str.length];
            for(int i = 0; i < str.length; i++)
            {
                received[i] = new JSONObject(str[i]);
                Log.wtf("Recieved", str[i]);
            }
//            is.close();
//            os.close();
            return received;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
