// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeAnimation.java

package jeu.jade.engine;

import java.awt.Image;

public class JadeAnimation
{

    public JadeAnimation(Image aimage[], int ai[], int ai1[], boolean flag, boolean flag1)
    {
        images = aimage;
        frames = ai;
        delays = ai1;
        loop = flag;
        pingpong = flag1;
    }

    public int delays[];
    public int frames[];
    public Image images[];
    public boolean loop;
    public boolean pingpong;
}
