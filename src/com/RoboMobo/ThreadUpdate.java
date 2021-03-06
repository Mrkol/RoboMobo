package com.RoboMobo;

import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 30.07.13
 * Time: 13:14
 */
public class ThreadUpdate extends Thread
{
    public long prevTime;
    public boolean isRunning;
    public SurfaceHolder surfaceHolder;
    public float rotation = 0;

    public ThreadUpdate(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;

        prevTime = System.currentTimeMillis();
        isRunning = true;
    }

    @Override
    public void run()
    {
        while (isRunning)
        {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30)
            {
                try
                {
                    RMR.c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder)
                    {

                        RMR.Update(elapsedTime);
                        if (RMR.c != null)
                        {
                            RMR.Draw();
                        }
                    }

                }

                finally
                {
                    if (RMR.c != null)
                    {
                        surfaceHolder.unlockCanvasAndPost(RMR.c);
                    }
                }
                prevTime = now;
            }
        }
    }


}
