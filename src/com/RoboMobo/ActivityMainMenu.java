package com.RoboMobo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 06.08.13
 * Time: 10:17
 */
public class ActivityMainMenu extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

    }

    public void play(View view)
    {
        Intent intent = new Intent(this, ActivityConnectMenu.class);
        startActivity(intent);
    }

    public void host(View view)
    {

    }

    public void connect(View view)
    {
        Intent intent = new Intent(this, ActivityConnectMenu.class);
        startActivity(intent);
    }
}