// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeImageBackground.java

package jeu.jade.engine;

import java.awt.Graphics;
import java.awt.Image;

// Referenced classes of package jade.engine:
//            JadeBackground

public class JadeImageBackground extends JadeBackground
{

    public final void clearRegion(int i, int j, int k, int l, Graphics g)
    {
        g.setClip(i, j, k, l);
        g.drawImage(image, sx, sy, null);
        g.setClip(0, 0, 10000, 10000);
    }

    public JadeImageBackground(Image image1)
    {
        image = image1;
    }

    Image image;	
}
