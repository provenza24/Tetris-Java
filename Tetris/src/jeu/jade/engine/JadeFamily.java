// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeFamily.java

package jeu.jade.engine;

import java.awt.Image;


// Referenced classes of package jade.engine:
//            JadeArray, JadeAnimation

public class JadeFamily extends JadeArray
{

    private JadeFamily(int i, int j)
    {
        data = null;
        width = i;
        height = j;
    }

    public JadeFamily()
    {
        this(0, 0);
    }

    public JadeFamily(JadeAnimation jadeanimation, int ai[])
    {
        this(jadeanimation.images[0].getWidth(null), jadeanimation.images[0].getHeight(null));
        animation = jadeanimation;
        representation = jadeanimation.images[jadeanimation.frames[0]];
        if(ai != null)
        {
            data = new int[ai.length];
            System.arraycopy(ai, 0, data, 0, ai.length);
        }
    }

    public JadeFamily(Image image, int ai[])
    {
        this(image.getWidth(null), image.getHeight(null));
        representation = image;
        if(ai != null)
        {
            data = new int[ai.length];
            System.arraycopy(ai, 0, data, 0, ai.length);
        }
    }

    public JadeFamily(long ai[])
    {
        this(0, 0);
        representation = null;
        if(ai != null)
        {
            data = new int[ai.length];
            System.arraycopy(ai, 0, data, 0, ai.length);
        }
    }

    public JadeAnimation animation;
    public Image representation;
    public int width;
    public int height;
    public int data[];
}
