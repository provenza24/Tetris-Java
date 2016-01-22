// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeImageMosaicBackground.java

package jeu.jade.engine;

import java.awt.Graphics;
import java.awt.Image;

// Referenced classes of package jade.engine:
//            JadeBackground

public class JadeImageMosaicBackground extends JadeBackground
{

    public final void clearRegion(int i, int j, int k, int l, Graphics g)
    {
        g.translate(sx, sy);
        i -= sx;
        j -= sy;
        g.setClip(i, j, k, l);
        int i1 = i / imgWidth;
        i1 *= imgWidth;
        int j1 = (i + k) / imgWidth;
        j1 *= imgWidth;
        int k1 = j / imgHeight;
        k1 *= imgHeight;
        int l1 = (j + l) / imgHeight;
        for(l1 *= imgHeight; k1 <= l1; k1 += imgHeight)
        {
            for(int i2 = i1; i2 <= j1; i2 += imgWidth)
                g.drawImage(image, i2, k1, null);

        }

        g.setClip(0, 0, 10000, 10000);
        g.translate(-sx, -sy);
    }

    public JadeImageMosaicBackground(Image image1)
    {
        image = image1;
        imgWidth = image1.getWidth(null);
        imgHeight = image1.getHeight(null);
    }

    Image image;
    int imgWidth;
    int imgHeight;
}
