// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeColoredBackground.java

package jeu.jade.engine;

import java.awt.Color;
import java.awt.Graphics;

// Referenced classes of package jade.engine:
//            JadeBackground

public class JadeColoredBackground extends JadeBackground
{

    public final void clearRegion(int i, int j, int k, int l, Graphics g)
    {
        g.setColor(color);
        g.fillRect(i, j, k, l);
    }

    public JadeColoredBackground(Color color1)
    {
        color = color1;
    }

    public Color color;
}
