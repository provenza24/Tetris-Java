// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeDisplay.java

package jeu.jade.engine;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import jeu.jade.loader.JadeContainer;


// Referenced classes of package jade.engine:
//            JadeBackground, JadeScene, JadeMath

public class JadeDisplay extends Canvas
{
	private static final long serialVersionUID = 42L;

    public final Graphics getDoubleBufferedGraphics()
    {
        if(backBuffer == null)
        {
            createBufferStrategy(2);
            strategy = getBufferStrategy();
            backBuffer = strategy.getDrawGraphics();
        }
        return backBuffer;
    }

    public final void paint(Graphics g)
    {
        swapBuffers();
    }

    public final void update(Graphics g)
    {
        paint(g);
    }

    public final void clearWithBackground(JadeBackground jadebackground)
    {
        jadebackground.clearRegion(0, 0, getWidth(), getHeight(), getDoubleBufferedGraphics());
    }

    public final void swapBuffers()
    {
        if(strategy != null)
            strategy.show();
    }

    public final void setKeyListener(KeyListener keylistener)
    {
        KeyListener akeylistener[] = getKeyListeners();
        for(int i = 0; i < akeylistener.length; i++)
            removeKeyListener(akeylistener[i]);

        if(keylistener != null)
            addKeyListener(keylistener);
    }

    public final void setMouseListener(MouseListener mouselistener)
    {
        MouseListener amouselistener[] = getMouseListeners();
        for(int i = 0; i < amouselistener.length; i++)
            removeMouseListener(amouselistener[i]);

        if(mouselistener != null)
            addMouseListener(mouselistener);
    }

    public final void setAnimationRate(int i)
    {
        if(i <= 0)
        {
            gameRate = timeToSleep = 0;
            return;
        } else
        {
            gameRate = (int)(1000F / (float)i);
            timeToSleep = (int)(500F / (float)gameRate);
            return;
        }
    }

    public final void stop()
    {
        running = false;
    }

    public final void start(JadeScene jadescene)
    {
        if(running)
            throw new Error("Engine already running !");
        jadescene.begin();
        running = true;
        lastTime = System.currentTimeMillis();
        timeToSleep = 0;
        while(running) 
        {            
            jadescene.mainLoop();
            swapBuffers();
            if(++elapsedFrame == 1)
            {
                long l1 = System.currentTimeMillis();
                ajustmentToSleep = (gameRate * 1 - (int)(l1 - lastTime)) / 1;
                timeToSleep = JadeMath.max(0, timeToSleep + ajustmentToSleep);
                lastTime = l1;
                elapsedFrame = 0;
            }
           /* try
            {
                Thread.sleep(timeToSleep);
            }
            catch(InterruptedException interruptedexception) { }*/
        }
        running = false;
        jadescene.end();        
    }

    public final void getBack()
    {
        running = true;
    }

    public JadeDisplay(int i, int j)
    {
        running = false;
        gameRate = 33;
        ajustmentToSleep = 0;
        elapsedFrame = 0;
        setSize(i, j);
    }

    public JadeDisplay(JadeContainer jadecontainer)
    {
        running = false;
        gameRate = 33;
        ajustmentToSleep = 0;
        elapsedFrame = 0;
        setSize(800,600);
        //setSize(((Component)jadecontainer).getWidth(), ((Component)jadecontainer).getHeight());
        jadecontainer.setContent(this);
    }
    
    private BufferStrategy strategy;
    private Graphics backBuffer;
    public boolean running;
    private int gameRate;
    private int timeToSleep;
    private int ajustmentToSleep;
    private long lastTime;
    private int elapsedFrame;
}
