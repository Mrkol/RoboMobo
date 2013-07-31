package com.RoboMobo;

import android.content.res.Resources;
import android.graphics.*;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: Роман
 * Date: 30.07.13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
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

                prevTime = now;
            }

            try
            {
                RMR.c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder)
                {
                    if(RMR.c != null)
                    {
                        //draw sheit

                        rotation += 4;

                        RMR.c.drawColor(Color.WHITE);

                        RMR.c.save();
                        {
                            RMR.c.translate(128, 128);
                            RMR.c.rotate(rotation);

                            Paint p = new Paint();
                            p.setColor(Color.CYAN);


                            Bitmap bitmap = BitmapFactory.decodeResource(RMR.am.getResources(), R.drawable.img_test);
                            //RMR.c.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), p);

                            p.setColor(Color.WHITE);
                            Rect r0 = new Rect();
                            r0.set(42, 14, 42 + 136, 14 + 110);
                            RectF r1 = new RectF();
                            r1.set(-32, -32, 64, 64);
                            RMR.c.drawBitmap(bitmap, r0, r1, p);
                        }
                        RMR.c.restore();
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
        }
    }
}
