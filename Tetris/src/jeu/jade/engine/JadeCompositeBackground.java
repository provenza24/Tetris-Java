// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeCompositeBackground.java

package jeu.jade.engine;

import java.awt.Graphics;

// Referenced classes of package jade.engine:
//            JadeBackground

public class JadeCompositeBackground extends JadeBackground
{

    public final void clearRegion(int i, int j, int k, int l, Graphics g)
    {
        g.translate(sx, sy);
        lower.clearRegion(i - sx, j - sy, k, l, g);
        upper.clearRegion(i - sx, j - sy, k, l, g);
        g.translate(-sx, -sy);
    }

    public JadeCompositeBackground(JadeBackground jadebackground, JadeBackground jadebackground1)
    {
        lower = jadebackground;
        upper = jadebackground1;
    }

    public JadeBackground upper;
    public JadeBackground lower;
}
