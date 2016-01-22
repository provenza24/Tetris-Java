// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeBackground.java

package jeu.jade.engine;

import java.awt.Graphics;

public abstract class JadeBackground
{

    public final void scrollTo(int i, int j)
    {
        sx = i;
        sy = j;
    }

    public final void scrollOf(int i, int j)
    {
        sx += i;
        sy += j;
    }
    
    public final void scrollOf(int i, int j, int p)
    {
    	if (pas==5){
    		sx += i;
    		sy += j;
    		pas=0;
    	} else {
    		pas = pas +p;
    	}
    }

    public abstract void clearRegion(int i, int j, int k, int l, Graphics g);

    public JadeBackground()
    {
    }

    public int sx;
    public int sy;
    
    public int pas=0;
    
}
