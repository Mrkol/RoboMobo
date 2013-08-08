package com.RoboMobo;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: loredan
 * Date: 03.08.13
 * Time: 10:05
 */
public class Networking
{
    public static HttpURLConnection connection;
    public static URL url;
    public static String msg;
    public static InputStream in;
    public static OutputStream out;

    public static void init(String ip, boolean server)
    {
        msg = ip;
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
    }

    public static JSONObject get(JSONObject send)
    {
        JSONObject received;
        try
        {
            connection = (HttpURLConnection) (new URL(msg+send.toString())).openConnection();
//            InputStream is = connection.getInputStream();
//            OutputStream os = connection.getOutputStream();
//            os.write(send.toString().getBytes());
//            while (is.available()==0);
//            byte[] raw = new byte[is.available()];
//            is.read(raw);
            received = new JSONObject(connection.getResponseMessage());
//            is.close();
//            os.close();
            return received;

        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
